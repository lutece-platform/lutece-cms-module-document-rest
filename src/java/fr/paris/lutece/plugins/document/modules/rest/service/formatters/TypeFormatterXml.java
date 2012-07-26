/*
 * Copyright (c) 2002-2012, Mairie de Paris
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

import fr.paris.lutece.plugins.document.business.DocumentType;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;


/**
 * TypeFormatterXml
 */
public class TypeFormatterXml implements IFormatter<DocumentType>
{
    /**
    * {@inheritDoc}
    */
    public String format( DocumentType resource )
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
         * Format DocumentType
         * @param listResources resources list
         * @param referenceItem referenceItem
         */
    public String format( List<DocumentType> listResources )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_DOCUMENTS );

        for ( DocumentType resource : listResources )
        {
            formatType( sbXml, resource );
        }

        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_DOCUMENTS );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    private static void formatType( StringBuffer sbXml, DocumentType type )
    {
        if ( type != null )
        {
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CODE_DOCUMENT_TYPE, type.getCode(  ) );

            if ( StringUtils.isNotBlank( type.getName(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE_NAME, type.getName(  ) );
            }

            if ( StringUtils.isNotBlank( type.getDescription(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE_DESCRIPTION, type.getDescription(  ) );
            }

            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE_THUMBNAIL_ATTRIBUTE_ID,
                type.getThumbnailAttributeId(  ) );

            if ( StringUtils.isNotBlank( type.getDefaultThumbnailUrl(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE_DEFAULT_THUMBNAIL_URL,
                    type.getDefaultThumbnailUrl(  ) );
            }

            if ( StringUtils.isNotBlank( type.getMetadataHandler(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE_METADATA_HANDLER,
                    type.getMetadataHandler(  ) );
            }

            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_TYPE );
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
