package biomesoplenty.client.handler;

import biomesoplenty.init.ModTags;
import net.minecraft.client.Camera;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class FluidFogHandler
{
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void getFogDensity(EntityViewRenderEvent.FogDensity event)
    {
        Camera camera = event.getInfo();

        FluidState fluidstate = camera.level.getFluidState(camera.blockPosition);
        if (fluidstate.is(ModTags.Fluids.BLOOD) && camera.position.y < (double)((float)camera.blockPosition.getY() + fluidstate.getHeight(camera.level, camera.blockPosition)))
        {
            event.setDensity(16.0F);
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void getFogColor(EntityViewRenderEvent.FogColors event)
    {
        Camera camera = event.getInfo();

        FluidState fluidstate = camera.level.getFluidState(camera.blockPosition);
        if (fluidstate.is(ModTags.Fluids.BLOOD))
        {
            event.setRed(0.443F);
            event.setGreen(0.141F);
            event.setBlue(0.149F);
        }
    }
}