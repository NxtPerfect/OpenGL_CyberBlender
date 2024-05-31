package OpenGLClass;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class blender extends JFrame implements GLEventListener {
	private volatile GLCanvas canvas;
	private FPSAnimator animator;
	private int kat = 0, owoceKat = 0;
	private int textureId = 0, skyboxTextureId = 0;
	private boolean owoceBlend = false;
	private final float CAMERA_DISTANCE = 8.0f;
	private final String SCIEZKA_OWOCE = "src/OpenGLClass/owoceOpt.jpg";
	private final String SCIEZKA_DZWIEK = "src/OpenGLClass/blender-mixer-smoothie-33026.wav";
	private final String SCIEZKA_SKYBOX = "src/OpenGLClass/skyboxOpt.jpg";
	private AudioInputStream audioInputStream;
	private Clip clip;
	private FloatControl fc;

	public blender() throws HeadlessException {
		super("Cyberblender");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create capabilities to add multisampling
		GLCapabilities capabilities = new GLCapabilities(GLProfile.getDefault());
		capabilities.setSampleBuffers(true);
		capabilities.setNumSamples(8);
		capabilities.setDoubleBuffered(true);

		// Create the GLCanvas and add it to the JFrame
		canvas = new GLCanvas(capabilities);
		canvas.addGLEventListener(this);
		getContentPane().add(canvas, BorderLayout.CENTER);

		// Add buttons to the JFrame
		animator = new FPSAnimator(canvas, 30);
		animator.start();

		// Sound on blending
		// lowered volume
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(SCIEZKA_DZWIEK));
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			fc.setValue(-30.0f);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Key Listener for spacebar
		KeyListener kl = new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
					owoceBlend = false;
					clip.stop();
					clip.setFramePosition(0);
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
					owoceBlend = true;
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
			}
		};
		addKeyListener(kl);

		setVisible(true);
	}

	@Override
	public void display(GLAutoDrawable glAutoDrawable) {
		GL2 gl = glAutoDrawable.getGL().getGL2();
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl.glLoadIdentity();

		gl.glTranslatef(0.0f, -2.0f, -10.0f);
		float scale[] = { 1.0f, 1.0f, 1.0f };
		gl.glRotatef(kat, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(180, 1.0f, 0.0f, 0.0f);
		gl.glScalef(scale[0], scale[1], scale[2]);

		float ambient[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float diffuse[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		float specular[] = { 0.0f, 0.0f, 0.0f, 0.0f };
//		float position[] = { 0.0f, 0.0f, -4.0f, 1.0f };

		// Skybox
		// Wczytanie teksture
		if (skyboxTextureId == 0) {
			try {
				Texture texture = TextureIO.newTexture(new File(SCIEZKA_SKYBOX), true);
				skyboxTextureId = texture.getTextureObject();

				// Set texture parameters
				gl.glBindTexture(GL2.GL_TEXTURE_2D, skyboxTextureId);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

				// Generate mipmaps for the texture
				gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		gl.glTranslatef(0.0f, -5.0f, 0.0f);
		Walec.DrawTeksturaOpt2(gl, 90.0f, 80.0f, 10, 10, skyboxTextureId, 0.0f);
		gl.glTranslatef(0.0f, 5.0f, 0.0f);

		// Stół
		ambient = new float[] { 1.0f, 0.0f, 0.0f, 0.2f };
		diffuse = new float[] { 0.8f, 0.2f, 0.8f, 0.2f };
		specular = new float[] { 0.2f, 0.2f, 0.2f, 0.2f };

		gl.glTranslatef(0.0f, 4.0f, 0.0f);
		Kostka.Draw(gl, 6.0f, 4);
		gl.glTranslatef(0.0f, -4.0f, 0.0f);

		// Podstawa
		double radiansRotation = Math.toRadians(kat);
		float z = (float) (Math.sin(radiansRotation) * CAMERA_DISTANCE);
		float x = (float) (Math.cos(radiansRotation) * CAMERA_DISTANCE);
		ambient = new float[] { 0.0f, 1.0f, 1.0f, 0.2f };
		diffuse = new float[] { 0.8f, 0.8f, 0.8f, 0.2f };
		specular = new float[] { 1.0f, 1.0f, 1.0f, 0.2f };
		float position[] = { x, 0.0f, z, 1.0f };

		changeLight(gl, ambient, diffuse, specular, position);

		Kostka.Draw(gl, 2.0f, 16);

		// Przyciski
		// Przycisk środek
		ambient = new float[] { 1.0f, 0.2f, 0.2f, 1.0f };
		diffuse = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		specular = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		changeLight(gl, ambient, diffuse, specular, position);

		gl.glTranslatef(0.0f, 0.0f, -1.0f);
		Kula.Draw(gl, 0.2f, 16, 4);
		gl.glTranslatef(0.0f, 0.0f, 1.0f);

		// Przycisk lewo
		ambient = new float[] { 0.2f, 1.0f, 0.2f, 1.0f };
		diffuse = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		specular = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		changeLight(gl, ambient, diffuse, specular, position);

		gl.glTranslatef(-0.75f, 0.0f, -1.0f);
		Kula.Draw(gl, 0.2f, 16, 4);
		gl.glTranslatef(0.75f, 0.0f, 1.0f);

		// Przycisk prawo
		ambient = new float[] { 0.2f, 0.2f, 1.0f, 1.0f };
		diffuse = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		specular = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		changeLight(gl, ambient, diffuse, specular, position);

		gl.glTranslatef(0.75f, 0.0f, -1.0f);
		Kula.Draw(gl, 0.2f, 8, 4);
		gl.glTranslatef(-0.75f, 0.0f, 1.0f);

		// Górne wieczko
		ambient = new float[] { 0.1f, 0.1f, 0.2f, 1.0f };
		diffuse = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		specular = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		changeLight(gl, ambient, diffuse, specular, position);

		gl.glTranslatef(0.0f, -4.125f, 0.0f);
		Walec.Draw(gl, 0.75f, 0.25f, 16, 4);
		Kolo.Draw(gl, 0.75f, 16, 16);
		gl.glTranslatef(0.0f, 1.625f, 0.0f);

		// Owoce
		ambient = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		diffuse = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		specular = new float[] { 0.2f, 0.2f, 0.2f, 1.0f };
		changeLight(gl, ambient, diffuse, specular, position);

		// Wczytaj teksture owocow
		if (textureId == 0) {
			try {
				Texture texture = TextureIO.newTexture(new File(SCIEZKA_OWOCE), true);
				textureId = texture.getTextureObject();

				// Set texture parameters
				gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR_MIPMAP_LINEAR);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

				// Generate mipmaps for the texture
				gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		gl.glTranslatef(0.0f, 1.625f, 0.0f);
		Walec.DrawTeksturaOpt2(gl, 0.74f, 3.0f, 16, 4, textureId, -owoceKat * 16);
		gl.glTranslatef(0.0f, -1.5f, 0.0f);
		Kolo.DrawTeksturaOpt2(gl, 0.75f, 16, 16, textureId, -owoceKat * 16);
		gl.glTranslatef(0.0f, -0.125f, 0.0f);

		// Rączka
		ambient = new float[] { 1.0f, 0.4f, 0.2f, 1.0f };
		diffuse = new float[] { 1.0f, 0.4f, 0.2f, 1.0f };
		specular = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		changeLight(gl, ambient, diffuse, specular, position);

		gl.glScalef(scale[0] / 1.25f, scale[1] * 1.5f, scale[2] / 2);
		gl.glTranslatef(-1.15f, 0.0f, 0.0f);
		Kostka.Draw(gl, 0.5f, 1);
		gl.glTranslatef(0.0f, -0.5f, 0.0f);
		Kostka.Draw(gl, 0.5f, 1);
		gl.glTranslatef(1.15f, 0.5f, 0.0f);
		gl.glScalef(scale[0] * 1.25f, scale[1] / 1.5f, scale[2] * 2);

		Walec.DrawTransparent(gl, 0.75f, 3.0f, 16, 2, 0.1f);

		kat += 1.0f;
		if (kat >= 360.0f) {
			kat -= 360.0f;
		}

		if (owoceBlend) {
			owoceKat += 1.0f;
		}
		if (owoceKat >= 360.0f) {
			owoceKat -= 360.0f;
		}

		canvas.repaint();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable glAutoDrawable) {
		// Initialize OpenGL settings
		GL2 gl = glAutoDrawable.getGL().getGL2();
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		float ambientlight[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glLightModelfv(GL2.GL_LIGHT_MODEL_AMBIENT, ambientlight, 0);

		gl.glEnable(GL2.GL_COLOR_MATERIAL_FACE);
		gl.glEnable(GL2.GL_EMISSION);
		float matAmbient[] = { 0.1f, 0.2f, 0.1f, 1.0f };
		float matSpec[] = { 0.0f, 0.5f, 0.0f, 1.0f };
		float emission[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		gl.glMaterialfv(GL2.GL_FRONT_FACE, GL2.GL_AMBIENT, matAmbient, 0);
		gl.glMaterialfv(GL2.GL_FRONT_FACE, GL2.GL_SPECULAR, matSpec, 0);
		gl.glMaterialfv(GL2.GL_FRONT_FACE, GL2.GL_EMISSION, emission, 0);
		gl.glMaterialf(GL2.GL_FRONT_FACE, GL2.GL_SHININESS, 1);

		gl.glEnable(GL2.GL_LIGHTING);

		float ambient[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		float diffuse[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		float specular[] = { 0.2f, 0.2f, 0.2f, 1.0f };
		float position[] = { -1.0f, 1.0f, 1.0f, 0.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);

		gl.glEnable(GL2.GL_LIGHT0);

		gl.glFlush();
	}

	@Override
	public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {
		// Adjust viewport and perspective when the window is resized
		GL2 gl = glAutoDrawable.getGL().getGL2();
		gl.glViewport(0, 0, width, height);
		if (height == 0)
			height = 1;

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		float aspectRatio = (float) width / height;
		GLU glu = new GLU();
		glu.gluPerspective(45.0, aspectRatio, 1.0, 100.0);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
	}

	private void changeLight(GL2 gl, float ambient[], float diffuse[], float specular[], float position[]) {
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuse, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, specular, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, position, 0);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new blender();
			}
		});
	}
}
