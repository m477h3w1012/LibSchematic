package com.tehbeard.forge.schematic.extensions;

import java.util.HashSet;
import java.util.Set;

import com.tehbeard.forge.schematic.SchematicFile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;

/**
 * Adds support for tagging a schematic
 * @author James
 *
 */
@SchExtension(name="Layered schematics",checkPath="metadata.tags")
public class TagsExtension implements SchematicExtension{

    private Set<String> tags = new HashSet<String>();
    
    @Override
    public void onLoad(NBTTagCompound tag,SchematicFile file) {
        NBTTagList list = tag.getCompoundTag("metadata").getTagList("tags");
        for(int i = 0;i<list.tagCount();i++){
            tags.add(((NBTTagString)list.tagAt(i)).data);
        }
    }

    @Override
    public void onSave(NBTTagCompound tag,SchematicFile file) {
        NBTTagList list = new NBTTagList("tags");
        for(String stag : tags){
            list.appendTag(new NBTTagString(null, stag));
        }
        if(!tag.hasKey("metadata")){
            tag.setCompoundTag("metadata", new NBTTagCompound());
        }
        tag.getCompoundTag("metadata").setTag("tags", list);
    }
    
    
    
    public boolean hasTag(String tag){
        return tags.contains(tag);
    }
    
    public void addTag(String tag){
        tags.add(tag);
    }
    
    public void removeTag(String tag){
        tags.remove(tag);
    }

}
