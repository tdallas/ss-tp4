package oscillator.integrators;

import oscillator.OscillatorParticle;

public class OscillatorGearIntegrator extends OscillatorIntegrator{
    private double[] previousRVector;

    public OscillatorGearIntegrator(double spring, double viscosity) {
        super(spring, viscosity);
    }

    @Override
    public void applyIntegrator(OscillatorParticle oscillatorParticle, double timeDelta) {
        if(this.previousRVector == null) {
            this.previousRVector = new double[6];
            this.previousRVector[0] = oscillatorParticle.getPosition();
            this.previousRVector[1] = oscillatorParticle.getVelocity();
            this.previousRVector[2] = getForces(oscillatorParticle.getPosition(), oscillatorParticle.getVelocity())/oscillatorParticle.getMass();
            this.previousRVector[3] = 0;
            this.previousRVector[4] = 0;
            this.previousRVector[5] = 0;
        }

        // multiplier
        double matrix[][] = {
                {1, timeDelta, Math.pow(timeDelta,2)/2, Math.pow(timeDelta,3)/6, Math.pow(timeDelta,4)/24, Math.pow(timeDelta,5)/120},
                {0, 1, timeDelta, Math.pow(timeDelta,2)/2, Math.pow(timeDelta,3)/6, Math.pow(timeDelta,4)/24},
                {0, 0, 1, timeDelta, Math.pow(timeDelta,2)/2, Math.pow(timeDelta,3)/6},
                {0, 0, 0, 1, timeDelta, Math.pow(timeDelta,2)/2},
                {0, 0, 0, 0, 1, timeDelta},
                {0, 0, 0, 0, 0, 1}};

        // initialize predicted r
        double RPredictedVector[] = new double[6];

        // calculates predicted r
        for(int i=0; i<6; i++) {
            for(int j=0; j<6; j++) {
                RPredictedVector[i] += matrix[i][j]*previousRVector[j];
            }
        }

        double r2tdeltaT = getForces(RPredictedVector[0], RPredictedVector[1])/oscillatorParticle.getMass();
        double r2ptdeltaT = RPredictedVector[2];
        double deltaA =r2tdeltaT - r2ptdeltaT; // TODO: check if this should be abs

        // correction
        double deltaR2 = deltaA * timeDelta * timeDelta / 2;

        // alphas to correct predicted values
        double c[] = {3/16.0, 251/360.0, 1, 11/18.0, 1/6.0, 1/60.0};

        // updates x and v
        oscillatorParticle.setPosition(RPredictedVector[0] + c[0]*deltaR2 * 0 / Math.pow(timeDelta, 0));
        oscillatorParticle.setVelocity(RPredictedVector[1] + c[1]*deltaR2 * 1 / Math.pow(timeDelta, 1));

        // apply corrections and save them
        for(int i=0; i<6; i++) {
            previousRVector[i] = RPredictedVector[i] + c[i]*deltaR2 * i / Math.pow(timeDelta, i);
        }
    }
}
