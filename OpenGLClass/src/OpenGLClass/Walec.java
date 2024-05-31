package OpenGLClass;

import com.jogamp.opengl.GL2;

public class Walec {
	public static void DrawTekstura(GL2 gl, double r, double h, int n, int m, int textureId) {
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n; j++) {
				gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
				gl.glPushAttrib(GL2.GL_CURRENT_BIT);
				
				// wczytaj teksture
				gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
				gl.glEnableClientState(GL2.GL_TEXTURE_COORD_ARRAY);

				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * i / (m - 1),
						r * Math.sin(2.0 * Math.PI * j / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * (i + 1) / (m - 1),
						r * Math.sin(2.0 * Math.PI * j / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * (i + 1) / (m - 1),
						r * Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * i / (m - 1),
						r * Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glEnd();
				gl.glPopAttrib();
			}
		}
	}

	public static void DrawTeksturaOpt(GL2 gl, double r, double h, int n, int m, int textureId, float textureRotationAngle) {
		gl.glEnable(GL2.GL_TEXTURE_2D);
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);

	    for (int i = 0; i < m - 1; i++) {
	        for (int j = 0; j < n; j++) {
	            double s0 = (double) j / n;
	            double s1 = (double) (j + 1) / n;
	            double t0 = (double) i / (m - 1);
	            double t1 = (double) (i + 1) / (m - 1);

	            gl.glBegin(GL2.GL_QUADS);

	            gl.glTexCoord2d(s0, t0);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * i / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * j / n));

	            gl.glTexCoord2d(s0, t1);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * (i + 1) / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * j / n));

	            gl.glTexCoord2d(s1, t1);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * (i + 1) / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * (j + 1) / n));

	            gl.glTexCoord2d(s1, t0);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * i / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * (j + 1) / n));

	            gl.glEnd();
	        }
	    }
	    gl.glDisable(GL2.GL_TEXTURE_2D);
	}

	public static void DrawTeksturaOpt2(GL2 gl, double r, double h, int n, int m, int textureId, float textureRotationAngle) {
	    gl.glEnable(GL2.GL_TEXTURE_2D);
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);

	    // Calculate the texture shift based on the rotation angle
	    double textureShift = textureRotationAngle / 360.0;

	    for (int i = 0; i < m - 1; i++) {
	        for (int j = 0; j < n; j++) {
	            // Calculate texture coordinates with the shift
	            double s0 = ((double) j / n) + textureShift;
	            double s1 = ((double) (j + 1) / n) + textureShift;
	            double t0 = (double) i / (m - 1);
	            double t1 = (double) (i + 1) / (m - 1);

	            gl.glBegin(GL2.GL_QUADS);

	            // First vertex
	            gl.glTexCoord2d(s0, t0);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * i / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * j / n));

	            // Second vertex
	            gl.glTexCoord2d(s0, t1);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * (i + 1) / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * j / n));

	            // Third vertex
	            gl.glTexCoord2d(s1, t1);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * (i + 1) / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * (j + 1) / n));

	            // Fourth vertex
	            gl.glTexCoord2d(s1, t0);
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * i / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * (j + 1) / n));

	            gl.glEnd();
	        }
	    }

	    gl.glDisable(GL2.GL_TEXTURE_2D);
	}
	public static void DrawTransparent(GL2 gl, double r, double h, int n, int m, float alpha) {

		gl.glEnable(GL2.GL_BLEND);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
		for (int i = 0; i < m - 1; i++) {
			for (int j = 0; j < n; j++) {
				gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
				gl.glPushAttrib(GL2.GL_CURRENT_BIT);
				gl.glColor4f(0.0f, 0.0f, 0.0f, alpha);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * i / (m - 1),
						r * Math.sin(2.0 * Math.PI * j / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * (i + 1) / (m - 1),
						r * Math.sin(2.0 * Math.PI * j / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * (i + 1) / (m - 1),
						r * Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * i / (m - 1),
						r * Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glEnd();
				gl.glPopAttrib();
			}
		}
		gl.glDisable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_LIGHTING);
	}

	public static void DrawTransparent2(GL2 gl, double r, double h, int n, int m, float alpha) {
	    gl.glEnable(GL2.GL_BLEND);
	    gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
	    gl.glDisable(GL2.GL_DEPTH_TEST); // Disable depth testing
	    gl.glDepthMask(false); // Disable depth mask

	    for (int i = 0; i < m - 1; i++) {
	        for (int j = 0; j < n; j++) {
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
	            gl.glPushAttrib(GL2.GL_CURRENT_BIT);
	            gl.glColor4f(0.0f, 0.0f, 0.0f, alpha);
	            gl.glBegin(GL2.GL_QUADS);
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * i / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * j / n));
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * (i + 1) / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * j / n));
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * (i + 1) / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * i / (m - 1),
	                    r * Math.sin(2.0 * Math.PI * (j + 1) / n));
	            gl.glEnd();
	            gl.glPopAttrib();
	        }
	    }

	    gl.glDepthMask(true); // Enable depth mask
	    gl.glEnable(GL2.GL_DEPTH_TEST); // Enable depth testing
	    gl.glDisable(GL2.GL_BLEND);
	}

	public static void Draw(GL2 gl, double r, double h, int n, int m) {

		for (int i = 0; i < m - 1; i++)
			for (int j = 0; j < n; j++) {
				gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
				gl.glPushAttrib(GL2.GL_CURRENT_BIT);
				if ((i + j) % 2 == 0)
					gl.glColor3f(0.0f, 0.0f, 0.0f);
				gl.glBegin(GL2.GL_QUADS);
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * i / (m - 1),
						r * Math.sin(2.0 * Math.PI * j / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * j / n), 0, Math.sin(2.0 * Math.PI * j / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * j / n), -h / 2 + h * (i + 1) / (m - 1),
						r * Math.sin(2.0 * Math.PI * j / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * (i + 1) / (m - 1),
						r * Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glNormal3d(Math.cos(2.0 * Math.PI * (j + 1) / n), 0, Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glVertex3d(r * Math.cos(2.0 * Math.PI * (j + 1) / n), -h / 2 + h * i / (m - 1),
						r * Math.sin(2.0 * Math.PI * (j + 1) / n));
				gl.glEnd();
				gl.glPopAttrib();
			}
	}
}
