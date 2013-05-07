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

import fr.paris.lutece.plugins.document.business.spaces.DocumentSpace;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.xml.XmlUtil;

import org.apache.commons.lang.StringUtils;

import java.util.List;


/**
 * SpaceFormatterXml
 */
public class SpaceFormatterXml implements IFormatter<DocumentSpace>
{
    /**
    * {@inheritDoc}
    */
    public String format( DocumentSpace resource )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );

        formatSpace( sbXml, resource );

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
         * Format DocumentSpace
         * @param listResources resources list
         * @param referenceItem referenceItem
         */
    public String format( List<DocumentSpace> listResources )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_SPACES );

        for ( DocumentSpace resource : listResources )
        {
            formatSpace( sbXml, resource );
        }

        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_SPACES );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    private static void formatSpace( StringBuffer sbXml, DocumentSpace space )
    {
        if ( space != null )
        {
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_SPACE );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_ID, space.getId(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_ID_PARENT, space.getIdParent(  ) );

            if ( StringUtils.isNotBlank( space.getName(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_NAME, space.getName(  ) );
            }

            if ( StringUtils.isNotBlank( space.getDescription(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_DESCRIPTION, space.getDescription(  ) );
            }

            if ( StringUtils.isNotBlank( space.getViewType(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_VIEW_TYPE, space.getViewType(  ) );
            }

            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_ID_ICON, space.getIdIcon(  ) );

            if ( StringUtils.isNotBlank( space.getIconUrl(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_ICON_URL, space.getIconUrl(  ) );
            }

            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_SPACE_ALLOWED_DOCUMENT_TYPES );

            for ( int i = 0; i < space.getAllowedDocumentTypes(  ).length; i++ )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_DOCUMENT_TYPE,
                    space.getAllowedDocumentTypes(  )[i] );
            }

            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_SPACE_ALLOWED_DOCUMENT_TYPES );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_DOCUMENT_CREATION_ALLOWED,
                String.valueOf( space.isDocumentCreationAllowed(  ) ) );

            if ( StringUtils.isNotBlank( space.getWorkgroup(  ) ) )
            {
                XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_SPACE_WORKGROUP, space.getWorkgroup(  ) );
            }

            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_SPACE );
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
