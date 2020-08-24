package org.redcastlemedia.multitallented.civs.spells.effects.particles;

import org.bukkit.Location;
import org.redcastlemedia.multitallented.civs.spells.effects.ParticleEffect;

public class FallingAura extends CivParticleEffect {
    @Override
    public void update(Object target, Location location, ParticleEffect particleEffect) {
        for (int i=0; i<5; i++) {
            particleEffect.spawnParticle(particleEffect.getParticleType(),
                    location.clone().add(getRandomXZ(), 1.2d + getRandomY(), getRandomXZ()),
                    particleEffect.getRed(), particleEffect.getGreen(), particleEffect.getBlue(),
                    particleEffect.getCount(), particleEffect.getSize(),
                    0, -0.1, 0,
                    particleEffect.getNote());
            particleEffect.spawnParticle(particleEffect.getParticleType(),
                    location.clone().add(getRandomXZ(), 0.2d + getRandomY(), getRandomXZ()),
                    particleEffect.getRed(), particleEffect.getGreen(), particleEffect.getBlue(),
                    particleEffect.getCount(), particleEffect.getSize(),
                    0, -0.1, 0,
                    particleEffect.getNote());
        }
    }

    private double getRandomY() {
        return Math.random() * 0.1F - 0.05F;
    }

    private double getRandomXZ() {
        return Math.random() * .7F - .35F;
    }

    @Override
    public long getRepeatDelay(ParticleEffect particleEffect) {
        return 1;
    }
}
