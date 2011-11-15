/*
 * Copyright (c) 2002-2011, Mairie de Paris
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
package fr.paris.lutece.plugins.document.modules.rest.util.builderxml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.locale.LocaleBeanUtils;

import fr.paris.lutece.plugins.document.business.DocumentType;
import fr.paris.lutece.plugins.document.business.DocumentTypeHome;
import fr.paris.lutece.plugins.document.business.attributes.AttributeTypeParameter;
import fr.paris.lutece.plugins.document.business.attributes.DocumentAttribute;
import fr.paris.lutece.plugins.document.business.attributes.DocumentAttributeHome;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.xml.XmlUtil;

/**
 * 
 * FieldsToCreateDocumentBuilderXml
 *
 */
public class FieldsToCreateDocumentBuilderXml
{
	
	/**
	 * Constructor
	 */
    FieldsToCreateDocumentBuilderXml(  )
    {
    }

    /**
     * Build the xml
     * @param strCodeDocumentType code type of document
     * @return the xml response
     */
    public static String buildXml( String strCodeDocumentType )
    {
        StringBuffer sbXml = new StringBuffer( AppPropertiesService.getProperty( DocumentRestConstants.PROPERTIES_XML_HEADER ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_STATUS, DocumentRestConstants.STATUS_SUCCESS );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM );

        DocumentType documentType = DocumentTypeHome.findByPrimaryKey( strCodeDocumentType );

        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_MAINFIELDS );
        mainFieldsBuildXml( sbXml );
        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_MAINFIELDS );

        if ( documentType.getMetadataHandler(  ).equals( "dublincore" ) )
        {
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_DUBLINCORE_FIELDS );
            dublinCodeMetaDatasFieldsBuildXml( sbXml );
            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_DUBLINCORE_FIELDS );
        }

        if ( ( documentType.getAttributes(  ) != null ) && !documentType.getAttributes(  ).isEmpty(  ) )
        {
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_ATTRIBUTS_FIELDS );
            documentAttributsFieldsBuildXml( documentType.getAttributes(  ), sbXml );
            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_ATTRIBUTS_FIELDS );
        }

        XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM );

        return sbXml.toString(  );
    }

    /**
     * Build the xml of main fiels
     * @param sbXml xml
     */
    private static void mainFieldsBuildXml( StringBuffer sbXml )
    {
        Map<String, String> mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_DOCUMENT_TITLE );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "text" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_MAXLENGTH, "255" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_SITE, "72" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "1" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, mapAttributsList );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_DOCUMENT_SUMMARY );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "text" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ROWS, "2" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_COLS, "60" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "1" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXTAREA, mapAttributsList );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_DOCUMENT_COMMENT );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "text" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ROWS, "2" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_COLS, "60" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXTAREA, mapAttributsList );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_PAGE_TEMPLATE_DOCUMENT_ID );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "radio" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ROWS, "2" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_COLS, "60" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
        XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_RADIO, mapAttributsList );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_RADIO_VALUE, "0" );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_RADIO_VALUE, "1" );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_RADIO_VALUE, "2" );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_VALIDITY_BEGIN );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "text" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_SITE, "10" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, mapAttributsList );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_VALIDITY_END );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "text" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_SITE, "10" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, mapAttributsList );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_ACCEPT_SITE_COMMENTS );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "checkbox" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_VALUE, "1" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_CHECKBOX, mapAttributsList );

        mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, DocumentRestConstants.PARAMETER_IS_MODERATED_COMMENT );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "checkbox" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_VALUE, "1" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
        XmlUtil.addEmptyElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_CHECKBOX, mapAttributsList );
    }

	/**
	 * Build the xml of dublin code meta datas
	 * @param sbXml xml
	 */
    private static void dublinCodeMetaDatasFieldsBuildXml( StringBuffer sbXml )
    {
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_TITLE ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "Mairie de Paris",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_CREATOR ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "lutece;portal;xml;java",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_SUBJECT ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_DESCRIPTION ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "Mairie de Paris",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_PUBLISHER ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_CONTRIBUTOR ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_DATE ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_TYPE ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_FORMAT ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_IDENTIFIER ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_SOURCE ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "fr",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_LANGUAGE ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_RELATION ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT, "",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_COVERAGE ) );
        XmlUtil.addElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_TEXT,
            "Copyrights (c) Mairie de Paris",
            getMapAttributDublinCodeMetaDatasFields( DocumentRestConstants.PARAMETER_DUBLIN_CORE_META_DATA_RIGHTS ) );
    }

    /**
     * Add attribut for dublin code meta datas
     * @param strName name of parameters
     * @return mapAttributsList map of attributs
     */
    private static Map<String, String> getMapAttributDublinCodeMetaDatasFields( String strName )
    {
        Map<String, String> mapAttributsList = new HashMap<String, String>(  );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, "text" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_SITE, "50" );
        mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, strName );

        return mapAttributsList;
    }

    /**
     * Build the xml of document attributs fields
     * @param listDocumentAttribute list of attributs
     * @param sbXml xml
     */
    private static void documentAttributsFieldsBuildXml( List<DocumentAttribute> listDocumentAttribute,
        StringBuffer sbXml )
    {
        for ( DocumentAttribute documentAttribute : listDocumentAttribute )
        {
            Map<String, String> mapAttributsList = new HashMap<String, String>(  );
            mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, documentAttribute.getName(  ) );
            mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_TYPE, documentAttribute.getCodeAttributeType(  ) );
            mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_CODE, documentAttribute.getCode(  ) );
            if ( documentAttribute.isRequired() )
            {
            	mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "1" );
            }
            else
            {
            	mapAttributsList.put( DocumentRestConstants.ATTRIBUTS_ISREQUIRED, "0" );
            }
            XmlUtil.beginElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_ATTRIBUT,
                mapAttributsList );

            List<AttributeTypeParameter> listAttributeTypeParameter = (List<AttributeTypeParameter>) DocumentAttributeHome.getAttributeParametersValues( documentAttribute.getId(  ),
                    LocaleBeanUtils.getDefaultLocale(  ) );

            if ( ( listAttributeTypeParameter != null ) && !listAttributeTypeParameter.isEmpty(  ) )
            {
                for ( AttributeTypeParameter attributeTypeParameter : listAttributeTypeParameter )
                {
                    Map<String, String> mapParametersAttributsList = new HashMap<String, String>(  );
                    mapParametersAttributsList.put( DocumentRestConstants.ATTRIBUTS_NAME, attributeTypeParameter.getName(  ) );
                    XmlUtil.beginElement( sbXml,
                        DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_PARAMETER_ATTRIBUT,
                        mapParametersAttributsList );

                    if ( ( attributeTypeParameter.getValueList(  ) != null ) &&
                            !attributeTypeParameter.getValueList(  ).isEmpty(  ) )
                    {
                        for ( String strValueParameter : attributeTypeParameter.getValueList(  ) )
                        {
                            XmlUtil.addElement( sbXml,
                                DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_PARAMETER_ATTRIBUT_VALUE,
                                strValueParameter );
                        }
                    }

                    XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_PARAMETER_ATTRIBUT );
                }
            }

            XmlUtil.endElement( sbXml, DocumentRestConstants.TAG_CREATE_DOCUMENT_FIELDS_FORM_ATTRIBUT );
        }
    }
}
