package com.tehbeard.forge.schematic.factory.worker;

import com.tehbeard.forge.schematic.SchematicDataRegistry;
import com.tehbeard.forge.schematic.SchematicFile;
import com.tehbeard.forge.schematic.handlers.SchematicDataHandler;
import com.tehbeard.forge.schematic.handlers.SchematicOwnerHandler;
import cpw.mods.fml.common.registry.GameData;

/**
 * Uses the {@link SchematicDataRegistry}s list of {@link SchematicDataHandler}
 * s, specifically {@link SchematicOwnerHandler}s to change the owner of blocks
 * in a schematic
 * 
 * @author James
 * 
 */
public class SetOwnerWorker extends AbstractSchematicWorker {

    private String newOwner;

    public SetOwnerWorker(String newOwner) {
        this.newOwner = newOwner;
    }

    @Override
    public SchematicFile modifySchematic(SchematicFile original) {
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                for (int z = 0; z < original.getLength(); z++) {
                    int b_id = GameData.getBlockRegistry()
                            .getId(original.getBlockNamespace(x, y, z));
                    SchematicDataHandler handler = SchematicDataRegistry
                            .getHandler(b_id);
                    if (handler == null) {
                        continue;
                    }
                    if (handler instanceof SchematicOwnerHandler) {
                        SchematicOwnerHandler ownerChanger = (SchematicOwnerHandler) handler;
                        ownerChanger.setOwner(original, x, y, z,
                                b_id,
                                original.getBlockData(x, y, z),
                                original.getTileEntityTagAt(x, y, z), newOwner);
                    }

                }
            }
        }

        return original;

    }
}
