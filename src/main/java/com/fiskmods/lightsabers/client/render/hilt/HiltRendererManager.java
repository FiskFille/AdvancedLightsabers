package com.fiskmods.lightsabers.client.render.hilt;

import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyDroideka;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyFulcrum;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyFury;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyGraflex;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyImperial;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyJuggernaut;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyKnighted;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyMandalorian;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyMauler;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyMechanical;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyProdigalSon;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyRebel;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyReborn;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyRedeemer;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelBodyVaid;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterDroideka;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterFulcrum;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterFury;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterGraflex;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterImperial;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterJuggernaut;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterKnighted;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterMandalorian;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterMauler;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterMechanical;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterProdigalSon;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterRebel;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterReborn;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterRedeemer;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelEmitterVaid;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelDroideka;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelFulcrum;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelFury;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelGraflex;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelImperial;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelJuggernaut;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelKnighted;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelMandalorian;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelMauler;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelMechanical;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelProdigalSon;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelRebel;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelReborn;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelRedeemer;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelPommelVaid;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionDroideka;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionFulcrum;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionFury;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionGraflex;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionImperial;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionJuggernaut;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionKnighted;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionMandalorian;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionMauler;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionMechanical;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionProdigalSon;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionRebel;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionReborn;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionRedeemer;
import com.fiskmods.lightsabers.client.model.lightsaber.ModelSwitchSectionVaid;
import com.fiskmods.lightsabers.common.hilt.HiltManager;

public class HiltRendererManager
{
    public static void register()
    {
        HiltRenderer.register(HiltManager.GRAFLEX, new HiltRendererBase(new ModelEmitterGraflex(), new ModelSwitchSectionGraflex(), new ModelBodyGraflex(), new ModelPommelGraflex()));
        HiltRenderer.register(HiltManager.REDEEMER, new HiltRendererBase(new ModelEmitterRedeemer(), new ModelSwitchSectionRedeemer(), new ModelBodyRedeemer(), new ModelPommelRedeemer()));
        HiltRenderer.register(HiltManager.MAULER, new HiltRendererBase(new ModelEmitterMauler(), new ModelSwitchSectionMauler(), new ModelBodyMauler(), new ModelPommelMauler()));
        HiltRenderer.register(HiltManager.PRODIGAL_SON, new HiltRendererBase(new ModelEmitterProdigalSon(), new ModelSwitchSectionProdigalSon(), new ModelBodyProdigalSon(), new ModelPommelProdigalSon()));
        HiltRenderer.register(HiltManager.KNIGHTED, new HiltRendererBase(new ModelEmitterKnighted(), new ModelSwitchSectionKnighted(), new ModelBodyKnighted(), new ModelPommelKnighted()));
        HiltRenderer.register(HiltManager.VAID_ANCIENT, new HiltRendererBase(new ModelEmitterVaid(), new ModelSwitchSectionVaid(), new ModelBodyVaid(), new ModelPommelVaid()));
        HiltRenderer.register(HiltManager.VAID_MODERN, new HiltRendererBase(new ModelEmitterVaid(), new ModelSwitchSectionVaid(), new ModelBodyVaid(), new ModelPommelVaid()));
        HiltRenderer.register(HiltManager.DROIDEKA, new HiltRendererBase(new ModelEmitterDroideka(), new ModelSwitchSectionDroideka(), new ModelBodyDroideka(), new ModelPommelDroideka()));
        HiltRenderer.register(HiltManager.FULCRUM, new HiltRendererBase(new ModelEmitterFulcrum(), new ModelSwitchSectionFulcrum(), new ModelBodyFulcrum(), new ModelPommelFulcrum()));
        HiltRenderer.register(HiltManager.JUGGERNAUT, new HiltRendererBase(new ModelEmitterJuggernaut(), new ModelSwitchSectionJuggernaut(), new ModelBodyJuggernaut(), new ModelPommelJuggernaut()));
        HiltRenderer.register(HiltManager.MECHANICAL, new HiltRendererBase(new ModelEmitterMechanical(), new ModelSwitchSectionMechanical(), new ModelBodyMechanical(), new ModelPommelMechanical()));
        HiltRenderer.register(HiltManager.MANDALORIAN, new HiltRendererBase(new ModelEmitterMandalorian(), new ModelSwitchSectionMandalorian(), new ModelBodyMandalorian(), new ModelPommelMandalorian()));
        HiltRenderer.register(HiltManager.FURY, new HiltRendererBase(new ModelEmitterFury(), new ModelSwitchSectionFury(), new ModelBodyFury(), new ModelPommelFury()));
        HiltRenderer.register(HiltManager.REBEL, new HiltRendererBase(new ModelEmitterRebel(), new ModelSwitchSectionRebel(), new ModelBodyRebel(), new ModelPommelRebel()));
        HiltRenderer.register(HiltManager.IMPERIAL, new HiltRendererBase(new ModelEmitterImperial(), new ModelSwitchSectionImperial(), new ModelBodyImperial(), new ModelPommelImperial()));
        HiltRenderer.register(HiltManager.REBORN, new HiltRendererOneTwelve(new ModelEmitterReborn(), new ModelSwitchSectionReborn(), new ModelBodyReborn(), new ModelPommelReborn()));
    }
}
