/**
 * 
 */
package org.hyperdata.beeps.filters;

/**
 * Lanczos resampler.
 * see http://en.wikipedia.org/wiki/Lanczos_resampling
 */
public class Resampler {

    float[][] sincTable;
    int sincable_fsize = 2000;
    int sinc_table_size = 5;
    int sinc_table_center = sinc_table_size / 2;

    public Resampler() {
        sincTable = new float[sincable_fsize][];
        for (int i = 0; i < sincable_fsize; i++) {
            sincTable[i] = generateSinc(sinc_table_size, -i
                            / ((float) sincable_fsize));
        }
    }

    // Normalized sinc function
    public static double sinc(double x) {
        return (x == 0.0) ? 1.0 : Math.sin(Math.PI * x) / (Math.PI * x);
    }

    // Generate sinc table
    public static float[] generateSinc(int size, float offset) {
        int center = size / 2;
        float[] w = new float[size];
        for (int k = 0; k < size; k++) {
            float x = (-center + k + offset);
            if (x < -2 || x > 2)
                w[k] = 0;
            else if (x == 0)
                w[k] = 1;
            else {
                w[k] = (float)(2.0 * Math.sin(Math.PI * x)
                                * Math.sin(Math.PI * x / 2.0)
                                / ((Math.PI * x) * (Math.PI * x)));
            }
        }
        return w;
    }

    public int getPadding() // must be at least half of sinc_table_size
    {
        return sinc_table_size / 2 + 2;
    }

    public void interpolate(float[] in, float[] in_offset, float in_end,
            float[] startpitch, float pitchstep, float[] out, int[] out_offset,
            int out_end) {
        float pitch = startpitch[0];
        float ix = in_offset[0];
        int ox = out_offset[0];
        float ix_end = in_end;
        int ox_end = out_end;

        if (pitchstep == 0) {
            while (ix < ix_end && ox < ox_end) {
                int iix = (int) ix;
                float[] sinc_table
                        = this.sincTable[(int) ((ix - iix) * sincable_fsize)];
                int xx = iix - sinc_table_center;
                float y = 0;
                for (int i = 0; i < sinc_table_size; i++, xx++)
                    y += in[xx] * sinc_table[i];
                out[ox++] = y;
                ix += pitch;
            }
        } else {
            while (ix < ix_end && ox < ox_end) {
                int iix = (int) ix;
                float[] sinc_table
                        = this.sincTable[(int) ((ix - iix) * sincable_fsize)];
                int xx = iix - sinc_table_center;
                float y = 0;
                for (int i = 0; i < sinc_table_size; i++, xx++)
                    y += in[xx] * sinc_table[i];
                out[ox++] = y;

                ix += pitch;
                pitch += pitchstep;
            }
        }
        in_offset[0] = ix;
        out_offset[0] = ox;
        startpitch[0] = pitch;

    }
}