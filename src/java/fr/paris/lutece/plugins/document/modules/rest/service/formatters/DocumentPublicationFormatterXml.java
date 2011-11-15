/**
 *
 */
package fr.paris.lutece.plugins.document.modules.rest.service.formatters;

import fr.paris.lutece.plugins.document.business.publication.DocumentPublication;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.rest.service.formatters.IFormatter;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.xml.XmlUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DocumentPublicationFormatterXml
 */
public class DocumentPublicationFormatterXml implements IFormatter<DocumentPublication>
{
	/**
     * {@inheritDoc}
     */
    public String format( DocumentPublication resource )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );

        formatDocumentPublication( sbXml, resource );

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * {@inheritDoc}
     */
    public String format( List<DocumentPublication> listResources )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_RESPONSE );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_DOCUMENTS_PUBLICATION );

        for ( DocumentPublication documentPublication : listResources )
        {
            formatDocumentPublication( sbXml, documentPublication );
        }

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_DOCUMENTS_PUBLICATION );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_RESPONSE );

        return sbXml.toString(  );
    }

    /**
     * Format DocumentPublication
     * @param sbXml xml
     * @param doc document
     */
    private static void formatDocumentPublication( StringBuffer sbXml, DocumentPublication documentPublication )
    {
        if ( documentPublication != null )
        {
            Map<String, String> mapAttributsDocumentPublication = new HashMap<String, String>(  );
            mapAttributsDocumentPublication.put( DocumentRestConstants.ATTRIBUTS_PORTLET_ID , String.valueOf( documentPublication.getPortletId(  ) ) );
            mapAttributsDocumentPublication.put( DocumentRestConstants.ATTRIBUTS_STATUS, String.valueOf( documentPublication.getStatus(  ) ) );
            XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_DOCUMENT_PUBLICATION,
                mapAttributsDocumentPublication );
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
