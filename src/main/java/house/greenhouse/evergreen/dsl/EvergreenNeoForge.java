package house.greenhouse.evergreen.dsl;

import house.greenhouse.evergreen.api.EvergreenPlatform;
import house.greenhouse.evergreen.util.EvergreenConstants;

public class EvergreenNeoForge extends EvergreenPlatform {

    public EvergreenNeoForge() {

    }

    @Override
    public String sourceSetName() {
        return EvergreenConstants.SourceSets.NEOFORGE;
    }
}
