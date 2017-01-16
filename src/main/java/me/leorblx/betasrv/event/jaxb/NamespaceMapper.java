package me.leorblx.betasrv.event.jaxb;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

public class NamespaceMapper extends NamespacePrefixMapper
{
    @Override
    public String getPreferredPrefix(String s, String s1, boolean b)
    {
        if ("http://schemas.datacontract.org/2004/07/EA.NFSWO.ENGINE.DataLayer.Serialization.LuckyDraw".equals(s)) {
            return "a";
        }
        
        return s1;
    }
}
