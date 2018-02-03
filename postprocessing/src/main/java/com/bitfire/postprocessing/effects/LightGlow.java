/*******************************************************************************
 * Copyright 2012 tsagrista
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/

package com.bitfire.postprocessing.effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessorEffect;
import com.bitfire.postprocessing.filters.Glow;

/**
 * Light scattering implementation.
 * 
 * @author Toni Sagrista
 */
public final class LightGlow extends PostProcessorEffect {
    public static class Settings {
        public final String name;

        public Settings(String name) {
            this.name = name;

        }

        public Settings(Settings other) {
            this.name = other.name;
        }
    }

    private Glow glow;
    private Settings settings;

    private boolean blending = false;
    private int sfactor, dfactor;

    public LightGlow(int fboWidth, int fboHeight) {
        glow = new Glow(fboWidth, fboHeight);

    }

    @Override
    public void dispose() {
        glow.dispose();
    }

    /** Sets the positions of the 10 lights in [0..1] in both coordinates **/
    public void setLightPositions(int nLights, float[] vec) {
        glow.setLightPositions(nLights, vec);
    }

    public void setLightViewAngles(float[] vec) {
        glow.setLightViewAngles(vec);
    }

    public void setLightColors(float[] vec) {
        glow.setLightColors(vec);
    }

    public void setNSamples(int nSamples) {
        glow.setNSamples(nSamples);
    }

    public void setTextureScale(float scl) {
        glow.setTextureScale(scl);
    }

    public void enableBlending(int sfactor, int dfactor) {
        this.blending = true;
        this.sfactor = sfactor;
        this.dfactor = dfactor;
    }

    public void disableBlending() {
        this.blending = false;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;

    }

    public void setLightGlowTexture(Texture tex) {
        glow.setLightGlowTexture(tex);
    }

    public Texture getLightGlowTexture() {
        return glow.getLightGlowTexture();
    }

    public void setPrePassTexture(Texture tex) {
        glow.setPrePassTexture(tex);
    }

    public Texture getPrePassTexture() {
        return glow.getPrePassTexture();
    }

    public boolean isBlendingEnabled() {
        return blending;
    }

    public int getBlendingSourceFactor() {
        return sfactor;
    }

    public int getBlendingDestFactor() {
        return dfactor;
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public void render(final FrameBuffer src, final FrameBuffer dest) {
        restoreViewport(dest);
        glow.setInput(src).setOutput(dest).render();
    }

    public void setViewportSize(int width, int height) {
        this.glow.setViewportSize(width, height);
    }

    @Override
    public void rebind() {
        glow.rebind();
    }
}
