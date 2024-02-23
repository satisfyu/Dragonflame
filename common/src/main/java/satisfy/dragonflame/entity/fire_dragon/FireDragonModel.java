package satisfy.dragonflame.entity.fire_dragon;

import satisfy.dragonflame.util.DragonflameIdentifier;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedGeoModel;

public class FireDragonModel extends DefaultedGeoModel<FireDragon> {

    public FireDragonModel() {
        super(new DragonflameIdentifier("firedragon"));
    }

    @Override
    protected String subtype() {
        return "entity";
    }


    @Override
    public void applyMolangQueries(FireDragon animatable, double animTime) {
        super.applyMolangQueries(animatable, animTime);
    }

    @Override
    public void setCustomAnimations(FireDragon animatable, long instanceId, AnimationState<FireDragon> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);



        /*
        float animBase = animationState.getPartialTick() * ((float) Math.PI) * 2;


        GeoBone[] tails = {getBone("bone10").get(), getBone("bone11").get(), getBone("bone12").get(), getBone("bone13").get()};
        GeoBone tail = getBone("Tail").get();


        //float pitchOfs = Mth.clamp(, -angleLimit, angleLimit);

        float tailRotationFactor = 1f; // Adjust this value to control the tail movement speed

        // Get the motion direction and rotation from the animatable object
        float motionDirectionY = animatable.getMotionDirection().getRotation().y;
        dragonflame.LOGGER.error("y: " + motionDirectionY);


        //float segmentRotationX = motionDirectionX * tailRotationFactor;
        float segmentRotationY = 0;
        if(motionDirectionY < -0.4){
            segmentRotationY = -tailRotationFactor;
        }
        else if(motionDirectionY > 0.4){
            segmentRotationY = tailRotationFactor;
        }

        dragonflame.LOGGER.error("tailRot: " + tail.getRotY());
        segmentRotationY = Mth.clamp(tail.getRotY() + segmentRotationY, -120, 120);
        dragonflame.LOGGER.error("segment: " + segmentRotationY);

        // Set the rotation of the segment around the X, Y, and Z axes
        //tail.setRotX(segmentRotationX);
        //tail.setRotY(segmentRotationY);

        tail.setRotY((float) Math.toRadians(segmentRotationY));

         */


    }




}