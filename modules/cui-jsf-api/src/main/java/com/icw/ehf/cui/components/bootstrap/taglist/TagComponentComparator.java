package com.icw.ehf.cui.components.bootstrap.taglist;

import java.util.Comparator;

import com.icw.ehf.cui.components.bootstrap.tag.TagComponent;

/**
 * @author Matthias Walliczek
 */
public class TagComponentComparator implements Comparator<TagComponent> {

    @Override
    public int compare(TagComponent o1, TagComponent o2) {
        if (o1.getTitleValue() != null && o2.getTitleValue() != null
                && !o1.getTitleValue().equals(o2.getTitleValue())) {
            return ((String) o1.getTitleValue()).compareTo((String) o2.getTitleValue());
        }
        return 0;
    }

}
