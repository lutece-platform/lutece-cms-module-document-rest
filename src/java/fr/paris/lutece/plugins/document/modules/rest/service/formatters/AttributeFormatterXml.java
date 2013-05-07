/*
 * Copyright (c) 2002-2013, Mairie de Paris
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

import fr.paris.lutece.plugins.document.business.attributes.DocumentAttribute;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;


/**
 * AttributeFormatterXml
 */
public class AttributeFormatterXml implements IFormatter<DocumentAttribute>
{
    /**
    * {@inheritDoc}
    */
    public String format( DocumentAttribute resource )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );

        formatType( sbXml, resource );

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
         * Format DocumentAttribute
         * @param listResources resources list
         * @param referenceItem referenceItem
         */
    public String format( List<DocumentAttribute> listResources )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_ATTRIBUTES );

        for ( DocumentAttribute resource : listResources )
        {
            formatType( sbXml, resource );
        }

        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_ATTRIBUTES );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    private static void formatType( StringBuffer sbXml, DocumentAttribute attribute )
    {
        if ( attribute != null )
        {
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_ATTRIBUTE );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_ATTRIBUTE_ID, attribute.getId(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CODE_ATTRIBUTE_TYPE, attribute.getCodeAttributeType(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CODE_ATTRIBUTE, attribute.getCode(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE_ATTRIBUTE_NAME, attribute.getName(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_ATTRIBUTE_DESCRIPTION, attribute.getDescription(  ) );
            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_ATTRIBUTE );
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
