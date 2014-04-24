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

import fr.paris.lutece.plugins.document.business.Document;
import fr.paris.lutece.plugins.document.business.DocumentHome;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.List;

import org.apache.commons.lang.StringUtils;

/** 
 * DocumentFormatterXml
 */
public class DocumentFormatterXml implements IFormatter<Document>
{
	/**
     * {@inheritDoc}
     */
    public String format( Document document )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );

        formatDocument( sbXml, document );

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    public String format( List<Document> listResources )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_DOCUMENTS );

        for ( Document document : listResources )
        {
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_ID_DOCUMENT, document.getId(  ) );
        }

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_DOCUMENTS );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * Format Document
     * @param sbXml xml
     * @param doc document
     */
    private void formatDocument( StringBuffer sbXml, Document doc )
    {
        if ( doc != null )
        {
            Document document = DocumentHome.findByPrimaryKey( doc.getId(  ) );
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_DOCUMENT );
            sbXml.append( document.getXmlWorkingContent(  ) );

            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_MAILING_LIST_ID, document.getMailingListId(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_PAGE_TEMPLATE_DOCUMENT_ID,
                document.getPageTemplateDocumentId(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATE_ID, document.getStateId( ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_PUBLISHED_STATUS, document.getPublishedStatus(  ) );
            XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_COMMENT, document.getComment(  ) );

            if ( StringUtils.isNotBlank( document.getXmlMetadata(  ) ) )
            {
                sbXml.append( document.getXmlMetadata(  ) );
            }

            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_DOCUMENT );
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
