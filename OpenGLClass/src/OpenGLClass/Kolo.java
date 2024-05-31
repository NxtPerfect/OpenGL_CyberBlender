package OpenGLClass;

import com.jogamp.opengl.GL2;

public class Kolo {
	public static void DrawTeksturaOpt(GL2 gl, double r, int n, int m, int textureId, float textureRotationAngle) {
	    double alpha, rr;
	    gl.glNormal3d(0.0, 1.0, 0.0);

	    // Bind the texture
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
	    gl.glEnable(GL2.GL_TEXTURE_2D);
	    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
	    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

	    // Draw the center triangle fan
	    gl.glBegin(GL2.GL_TRIANGLE_FAN);
	    gl.glTexCoord2d(0.5, 0.5); // Center of the texture
	    gl.glVertex3d(0, 0, 0); // Center of the circle
	    for (int i = 0; i <= n; i++) {
	        alpha = 2.0 * Math.PI * i / n;
	        rr = r;
	        double x = rr * Math.cos(alpha);
	        double z = rr * Math.sin(alpha);
	        double u = 0.5 + 0.5 * Math.cos(alpha); // Texture coordinate u
	        double v = 0.5 + 0.5 * Math.sin(alpha); // Texture coordinate v
	        gl.glTexCoord2d(u, v);
	        gl.glVertex3d(x, 0, z);
	    }
	    gl.glEnd();

	    // Draw the quads for the ring
	    for (int j = 1; j < m; j++) {
	        gl.glBegin(GL2.GL_QUAD_STRIP);
	        for (int i = 0; i <= n; i++) {
	            alpha = 2.0 * Math.PI * i / n;

	            rr = r * (j - 1) / (m - 1);
	            double x1 = rr * Math.cos(alpha);
	            double z1 = rr * Math.sin(alpha);
	            double u1 = 0.5 + 0.5 * (rr / r) * Math.cos(alpha);
	            double v1 = 0.5 + 0.5 * (rr / r) * Math.sin(alpha);
	            gl.glTexCoord2d(u1, v1);
	            gl.glVertex3d(x1, 0, z1);

	            rr = r * j / (m - 1);
	            double x2 = rr * Math.cos(alpha);
	            double z2 = rr * Math.sin(alpha);
	            double u2 = 0.5 + 0.5 * (rr / r) * Math.cos(alpha);
	            double v2 = 0.5 + 0.5 * (rr / r) * Math.sin(alpha);
	            gl.glTexCoord2d(u2, v2);
	            gl.glVertex3d(x2, 0, z2);
	        }
	        gl.glEnd();
	    }

	    gl.glDisable(GL2.GL_TEXTURE_2D);
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0); // Unbind the texture
	}

	public static void DrawTeksturaOpt2(GL2 gl, double r, int n, int m, int textureId, float textureRotationAngle) {
	    double alpha, rr;

	    // Enable and bind the texture
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, textureId);
	    gl.glEnable(GL2.GL_TEXTURE_2D);
	    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
	    gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);

	    // Convert the texture rotation angle to a shift value
	    double textureShift = textureRotationAngle / 360.0;

	    // Draw the center triangle fan
	    gl.glBegin(GL2.GL_TRIANGLE_FAN);
	    gl.glTexCoord2d(0.5 + textureShift, 0.5); // Center of the texture
	    gl.glVertex3d(0, 0, 0); // Center of the circle
	    for (int i = 0; i <= n; i++) {
	        alpha = 2.0 * Math.PI * i / n;
	        rr = r;
	        double x = rr * Math.cos(alpha);
	        double z = rr * Math.sin(alpha);
	        double u = 0.5 + 0.5 * Math.cos(alpha) + textureShift; // Adjust texture coordinate u
	        double v = 0.5 + 0.5 * Math.sin(alpha); // Texture coordinate v
	        gl.glTexCoord2d(u, v);
	        gl.glVertex3d(x, 0, z);
	    }
	    gl.glEnd();

	    // Draw the quads for the ring
	    for (int j = 1; j < m; j++) {
	        gl.glBegin(GL2.GL_QUAD_STRIP);
	        for (int i = 0; i <= n; i++) {
	            alpha = 2.0 * Math.PI * i / n;

	            rr = r * (j - 1) / (m - 1);
	            double x1 = rr * Math.cos(alpha);
	            double z1 = rr * Math.sin(alpha);
	            double u1 = 0.5 + 0.5 * (rr / r) * Math.cos(alpha) + textureShift; // Adjust texture coordinate u1
	            double v1 = 0.5 + 0.5 * (rr / r) * Math.sin(alpha); // Texture coordinate v1
	            gl.glTexCoord2d(u1, v1);
	            gl.glVertex3d(x1, 0, z1);

	            rr = r * j / (m - 1);
	            double x2 = rr * Math.cos(alpha);
	            double z2 = rr * Math.sin(alpha);
	            double u2 = 0.5 + 0.5 * (rr / r) * Math.cos(alpha) + textureShift; // Adjust texture coordinate u2
	            double v2 = 0.5 + 0.5 * (rr / r) * Math.sin(alpha); // Texture coordinate v2
	            gl.glTexCoord2d(u2, v2);
	            gl.glVertex3d(x2, 0, z2);
	        }
	        gl.glEnd();
	    }

	    gl.glDisable(GL2.GL_TEXTURE_2D);
	    gl.glBindTexture(GL2.GL_TEXTURE_2D, 0); // Unbind the texture
	}

	public static void Draw(GL2 gl, double r, int n, int m) {

		double alpha, rr;
		gl.glNormal3d(0.0, 1.0, 0.0);
		for (int i = 0; i < n; i++) {
			gl.glPushAttrib(GL2.GL_CURRENT_BIT);
			if (i % 2 == 0)
				gl.glColor3f(0.0f, 0.0f, 0.0f);
			gl.glBegin(GL2.GL_TRIANGLES);
			gl.glVertex3d(0, 0, 0);
			alpha = 2.0 * Math.PI * i / n;
			rr = r / (m - 1);
			gl.glVertex3d(rr * Math.cos(alpha), 0, rr * Math.sin(alpha));
			alpha = 2.0 * Math.PI * (i - 1) / n;
			rr = r / (m - 1);
			gl.glVertex3d(rr * Math.cos(alpha), 0, rr * Math.sin(alpha));
			gl.glEnd();
			gl.glPopAttrib();
		}
		for (int j = 2; j < m; j++) {
			for (int i = 0; i < n; i++) {
				gl.glPushAttrib(GL2.GL_CURRENT_BIT);
				if ((i + j + 1) % 2 == 0)
					gl.glColor3f(0.0f, 0.0f, 0.0f);
				gl.glBegin(GL2.GL_QUADS);
				rr = r * j / (m - 1);
				alpha = 2.0 * Math.PI * i / n;
				gl.glVertex3d(rr * Math.cos(alpha), 0, rr * Math.sin(alpha));
				rr = r * j / (m - 1);
				alpha = 2.0 * Math.PI * (i - 1) / n;
				gl.glVertex3d(rr * Math.cos(alpha), 0, rr * Math.sin(alpha));
				rr = r * (j - 1) / (m - 1);
				alpha = 2.0 * Math.PI * (i - 1) / n;
				gl.glVertex3d(rr * Math.cos(alpha), 0, rr * Math.sin(alpha));
				rr = r * (j - 1) / (m - 1);
				alpha = 2.0 * Math.PI * i / n;
				gl.glVertex3d(rr * Math.cos(alpha), 0, rr * Math.sin(alpha));
				gl.glEnd();
				gl.glPopAttrib();
			}
		}

	}
}
