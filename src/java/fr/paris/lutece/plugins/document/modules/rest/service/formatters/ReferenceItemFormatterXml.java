/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.document.modules.rest.service.formatters;

import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ReferenceItemFormatterXml
 */
public class ReferenceItemFormatterXml implements IFormatter<ReferenceItem>
{
	/**
     * {@inheritDoc}
     */
    public String format( ReferenceItem resource )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );

        formatReferenceItem( sbXml, resource );

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    public String format( List<ReferenceItem> listResources )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_REF_ITEMS );

        for ( ReferenceItem referenceItem : listResources )
        {
            formatReferenceItem( sbXml, referenceItem );
        }

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_REF_ITEMS );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }
    
	/**
	 * Format formatReferenceItem
	 * @param sbXml xml
	 * @param referenceItem referenceItem
	 */
    private static void formatReferenceItem( StringBuffer sbXml, ReferenceItem referenceItem )
    {
        if ( referenceItem != null )
        {
            Map<String, String> mapAttributsReferenceItem = new HashMap<String, String>(  );
            mapAttributsReferenceItem.put( DocumentRestConstants.ATTRIBUTS_NAME, referenceItem.getName(  ) );
            mapAttributsReferenceItem.put( DocumentRestConstants.ATTRIBUTS_CODE, referenceItem.getCode(  ) );
            XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_REF_ITEM, mapAttributsReferenceItem );
        }
    }

    /**
     * {@inheritDoc}
     */
    public String formatError( String strCode, String strMessage )
    {
        return null;
    }
}
