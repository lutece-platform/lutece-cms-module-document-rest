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
package fr.paris.lutece.plugins.document.modules.rest.rs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.document.business.Document;
import fr.paris.lutece.plugins.document.business.DocumentHome;
import fr.paris.lutece.plugins.document.business.DocumentType;
import fr.paris.lutece.plugins.document.business.DocumentTypeHome;
import fr.paris.lutece.plugins.document.business.IndexerAction;
import fr.paris.lutece.plugins.document.business.attributes.DocumentAttribute;
import fr.paris.lutece.plugins.document.business.category.Category;
import fr.paris.lutece.plugins.document.business.category.CategoryHome;
import fr.paris.lutece.plugins.document.business.portlet.DocumentListPortletHome;
import fr.paris.lutece.plugins.document.business.portlet.DocumentPortletHome;
import fr.paris.lutece.plugins.document.business.portlet.PortletFilter;
import fr.paris.lutece.plugins.document.business.portlet.PortletOrder;
import fr.paris.lutece.plugins.document.business.publication.DocumentPublication;
import fr.paris.lutece.plugins.document.business.publication.DocumentPublicationHome;
import fr.paris.lutece.plugins.document.business.spaces.DocumentSpace;
import fr.paris.lutece.plugins.document.business.spaces.DocumentSpaceHome;
import fr.paris.lutece.plugins.document.business.workflow.DocumentAction;
import fr.paris.lutece.plugins.document.business.workflow.DocumentActionHome;
import fr.paris.lutece.plugins.document.modules.rest.util.builderxml.FieldsToCreateDocumentBuilderXml;
import fr.paris.lutece.plugins.document.modules.rest.util.builderxml.ResponseActionBuilderXml;
import fr.paris.lutece.plugins.document.modules.rest.util.builderxml.AddHeaderXml;
import fr.paris.lutece.plugins.document.modules.rest.util.constants.DocumentRestConstants;
import fr.paris.lutece.plugins.document.service.AttributeManager;
import fr.paris.lutece.plugins.document.service.AttributeService;
import fr.paris.lutece.plugins.document.service.DocumentException;
import fr.paris.lutece.plugins.document.service.DocumentPlugin;
import fr.paris.lutece.plugins.document.service.DocumentService;
import fr.paris.lutece.plugins.document.service.DocumentTypeResourceIdService;
import fr.paris.lutece.plugins.document.service.metadata.MetadataHandler;
import fr.paris.lutece.plugins.document.service.publishing.PublishingService;
import fr.paris.lutece.plugins.document.service.search.DocumentIndexer;
import fr.paris.lutece.plugins.document.service.spaces.DocumentSpacesService;
import fr.paris.lutece.plugins.document.utils.DocumentIndexerUtils;
import fr.paris.lutece.plugins.rest.service.RestConstants;
import fr.paris.lutece.portal.business.resourceenhancer.ResourceEnhancer;
import fr.paris.lutece.portal.business.user.AdminUser;
import fr.paris.lutece.portal.business.user.AdminUserHome;
import fr.paris.lutece.portal.service.search.IndexationService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.portal.web.upload.MultipartHttpServletRequest;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.date.DateUtil;
import fr.paris.lutece.util.html.HtmlTemplate;
import fr.paris.lutece.util.http.MultipartUtil;
import fr.paris.lutece.util.string.StringUtil;


/**
 * DocumentRest
 */
@Path( RestConstants.BASE_PATH + DocumentPlugin.PLUGIN_NAME )
public class DocumentRest
{
    /**
    * Get the wadl.xml content
    * @param request {@link HttpServletRequest}
    * @return the content of wadl.xml
    */
    @GET
    @Path( DocumentRestConstants.PATH_WADL )
    @Produces( MediaType.APPLICATION_XML )
    public String getWADL( @Context
    HttpServletRequest request )
    {
        StringBuilder sbBase = new StringBuilder( AppPathService.getBaseUrl( request ) );

        if ( sbBase.toString(  ).endsWith( DocumentRestConstants.SLASH ) )
        {
            sbBase.deleteCharAt( sbBase.length(  ) - 1 );
        }

        sbBase.append( RestConstants.BASE_PATH + DocumentPlugin.PLUGIN_NAME );

        Map<String, Object> model = new HashMap<String, Object>(  );
        model.put( DocumentRestConstants.MARK_BASE_URL, sbBase.toString(  ) );

        HtmlTemplate t = AppTemplateService.getTemplate( DocumentRestConstants.TEMPLATE_WADL, request.getLocale(  ),
                model );

        return t.getHtml(  );
    }

    /**
     * Get document spaces by id user
     * @param strIdUser the id user
     * @return the xml of spaces
     */
    @GET
    @Path( DocumentRestConstants.PATH_GET_USER_SPACES )
    @Produces( MediaType.APPLICATION_XML )
    public String getDocumentSpaces( @PathParam( DocumentRestConstants.PARAMETER_ID_USER )
    String strIdUser )
    {
        if ( StringUtils.isNotBlank( strIdUser ) && StringUtils.isNumeric( strIdUser ) )
        {
            //get AdminUser
            int nIdUser = Integer.parseInt( strIdUser );
            AdminUser user = AdminUserHome.findByPrimaryKey( nIdUser );

            if ( user != null )
            {
                user.setRights( AdminUserHome.getRightsListForUser( nIdUser ) );
                user.setRoles( AdminUserHome.getRolesListForUser( nIdUser ) );

                // Spaces
                String strXmlSpaces = DocumentSpacesService.getInstance(  ).getXmlSpacesList( user );
                return AddHeaderXml.addHeaderXml( strXmlSpaces );
            }
        }
        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Get space by id
     * @param strIdSpace the id space
     * @return the xml of space informations
     */
    @GET
    @Path( DocumentRestConstants.PATH_GET_SPACE )
    @Produces( MediaType.APPLICATION_XML )
    public List<DocumentSpace> getSpaceById( @PathParam( DocumentRestConstants.PARAMETER_ID_SPACE )
    String strIdSpace )
    {
        if ( StringUtils.isNotBlank( strIdSpace ) && StringUtils.isNumeric( strIdSpace ) )
        {
            int nIdSpace = Integer.parseInt( strIdSpace );
            List<DocumentSpace> documentSpaceList = new ArrayList<DocumentSpace>(  );
            DocumentSpace documentSpace = DocumentSpaceHome.findByPrimaryKey( nIdSpace );
            documentSpaceList.add( documentSpace );

            return documentSpaceList;
        }

        return null;
    }

    /**
     * Get documents list by id space
     * @param strIdSpace the id space
     * @return the xml of documents list
     */
    @GET
    @Path( DocumentRestConstants.PATH_GET_DOCUMENTS_LIST_BY_SPACE )
    @Produces( MediaType.APPLICATION_XML )
    public List<Document> getDocumentsList( @PathParam( DocumentRestConstants.PARAMETER_ID_SPACE )
    String strIdSpace )
    {
        if ( StringUtils.isNotBlank( strIdSpace ) && StringUtils.isNumeric( strIdSpace ) )
        {
            int nIdSpace = Integer.parseInt( strIdSpace );
            List<Document> documentList = DocumentHome.findBySpaceKey( nIdSpace );

            return documentList;
        }

        return null;
    }

    /**
     * Get document by id document
     * @param strIdDocument the id document
     * @return the xml of document
     */
    @GET
    @Path( DocumentRestConstants.PATH_GET_DOCUMENT )
    @Produces( MediaType.APPLICATION_XML )
    public List<Document> getDocument( @PathParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument )
    {
        if ( StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdDocument = Integer.parseInt( strIdDocument );
            List<Document> documentList = new ArrayList<Document>(  );
            Document document = DocumentHome.findByPrimaryKey( nIdDocument );
            documentList.add( document );

            return documentList;
        }

        return null;
    }

    /**
     * Get different type document fields to create it
     * @param strCodeDocumentType type document code
     * @return
     */
    @GET
    @Path( DocumentRestConstants.PATH_CREATE_GET_FIELDS )
    @Produces( MediaType.APPLICATION_XML )
    public String getCreatingDocumentFields( 
        @PathParam( DocumentRestConstants.PARAMETER_CODE_DOCUMENT_TYPE )
    String strCodeDocumentType )
    {
        if ( StringUtils.isNotBlank( strCodeDocumentType ) )
        {
            return FieldsToCreateDocumentBuilderXml.buildXml( strCodeDocumentType );
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

     /**
     * Get list of different types document
     * @param strCodeDocumentType type document code
     * @return
     */
    @GET
    @Path( DocumentRestConstants.PATH_GET_LIST_DOCUMENT_TYPE )
    @Produces( MediaType.APPLICATION_XML )
    public List<DocumentType> getListDocumentTypes( )
    {
        ArrayList<DocumentType> documentTypesList = (ArrayList<DocumentType>) DocumentTypeHome.findAll();

        return documentTypesList;
    }

    /**
     * Remove the document by id
     * @param strIdDocument id document
     * @return
     */
    @DELETE
    @Path( DocumentRestConstants.PATH_REMOVE_DOCUMENT )
    @Produces( MediaType.APPLICATION_XML )
    public String getDeletingDocument( @PathParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument )
    {
        if ( StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nDocumentId = Integer.parseInt( strIdDocument );
            DocumentHome.remove( nDocumentId );

            return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Get the list of document portlets
     * @param strIdDocument id document
     * @return listPortlet portlets list (will be formatted in xml format)
     */
    @GET
    @Path( DocumentRestConstants.PATH_PORTLETS_DOCUMENT )
    @Produces( MediaType.APPLICATION_XML )
    public List<ReferenceItem> getPortletsToAssignDocument( 
        @PathParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument )
    {
        if ( StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nDocumentId = Integer.parseInt( strIdDocument );
            PortletOrder pOrder = new PortletOrder(  );
            PortletFilter pFilter = new PortletFilter(  );
            List<ReferenceItem> listPortlet = (List<ReferenceItem>) DocumentPortletHome.findByCodeDocumentTypeAndCategory( nDocumentId,
                    DocumentHome.findByPrimaryKey( nDocumentId ).getCodeDocumentType(  ), pOrder, pFilter );

            return listPortlet;
        }

        return null;
    }

    /**
     * Get the list of list documents portlets
     * @param strIdDocument
     * @return listPortlet portlets list (will be formatted in xml format)
     */
    @GET
    @Path( DocumentRestConstants.PATH_PORTLETS_LIST_DOCUMENT )
    @Produces( MediaType.APPLICATION_XML )
    public List<ReferenceItem> getPortletsListToAssignDocument( 
        @PathParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument )
    {
        if ( StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nDocumentId = Integer.parseInt( strIdDocument );
            PortletOrder pOrder = new PortletOrder(  );
            PortletFilter pFilter = new PortletFilter(  );
            List<ReferenceItem> listPortletList = (List<ReferenceItem>) DocumentListPortletHome.findByCodeDocumentTypeAndCategory( nDocumentId,
                    DocumentHome.findByPrimaryKey( nDocumentId ).getCodeDocumentType(  ), pOrder, pFilter );

            return listPortletList;
        }

        return null;
    }

    /**
     * Get the list of document/list documents portlets where the document is assigned
     * @param strIdDocument id document
     * @return listDocumentPublication DocumentPublication list (will be formatted in xml format)
     */
    @GET
    @Path( DocumentRestConstants.PATH_PORTLETS_DOCUMENT_ASSIGNED )
    @Produces( MediaType.APPLICATION_XML )
    public List<DocumentPublication> getPortletsToPublishDocument( 
        @PathParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument )
    {
        if ( StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nDocumentId = Integer.parseInt( strIdDocument );

            List<DocumentPublication> listDocumentPublication = (List<DocumentPublication>) DocumentPublicationHome.findByDocumentId( nDocumentId );

            return listDocumentPublication;
        }

        return null;
    }

    /**
     * Method to submit or not a document to validate it
     * @param strIdDocument id document
     * @param strIdAction id action
     * @param strIdUser id user
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_DOCUMENT_SUBMIT )
    @Produces( MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doSubmitOrUnsubmitDocumentToValidate( 
        @FormParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument, @FormParam( DocumentRestConstants.PARAMETER_ID_ACTION )
    String strIdAction, @FormParam( DocumentRestConstants.PARAMETER_ID_USER )
    String strIdUser, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdUser ) && StringUtils.isNumeric( strIdUser ) &&
                StringUtils.isNotBlank( strIdAction ) && StringUtils.isNumeric( strIdAction ) &&
                StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdAction = Integer.parseInt( strIdAction );
            int nIdDocument = Integer.parseInt( strIdDocument );
            int nIdUser = Integer.parseInt( strIdUser );
            AdminUser user = AdminUserHome.findByPrimaryKey( nIdUser );

            if ( user != null )
            {
                user.setRights( AdminUserHome.getRightsListForUser( nIdUser ) );
                user.setRoles( AdminUserHome.getRolesListForUser( nIdUser ) );

                Document document = DocumentHome.findByPrimaryKeyWithoutBinaries( nIdDocument );
                DocumentAction action = DocumentActionHome.findByPrimaryKey( nIdAction );

                if ( ( action == null ) || ( action.getFinishDocumentState(  ) == null ) || ( document == null ) ||
                        !DocumentService.getInstance(  )
                                            .isAuthorizedAdminDocument( document.getSpaceId(  ),
                            document.getCodeDocumentType(  ), action.getPermission(  ), user ) )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                try
                {
                    DocumentService.getInstance(  )
                                   .changeDocumentState( document, user, action.getFinishDocumentState(  ).getId(  ) );
                }
                catch ( DocumentException e )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
            }
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Validate a document
     * @param strIdDocument id document
     * @param strIdAction id action
     * @param strIdUser id user
     * @param request request
     * @return response
     */
    @POST
    @Path( DocumentRestConstants.PATH_DOCUMENT_VALIDATE )
    @Produces( MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doValidateDocument( @FormParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument, @FormParam( DocumentRestConstants.PARAMETER_ID_ACTION )
    String strIdAction, @FormParam( DocumentRestConstants.PARAMETER_ID_USER )
    String strIdUser, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdUser ) && StringUtils.isNumeric( strIdUser ) &&
                StringUtils.isNotBlank( strIdAction ) && StringUtils.isNumeric( strIdAction ) &&
                StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdAction = Integer.parseInt( strIdAction );
            int nIdDocument = Integer.parseInt( strIdDocument );
            int nIdUser = Integer.parseInt( strIdUser );
            AdminUser user = AdminUserHome.findByPrimaryKey( nIdUser );

            if ( user != null )
            {
                user.setRights( AdminUserHome.getRightsListForUser( nIdUser ) );
                user.setRoles( AdminUserHome.getRolesListForUser( nIdUser ) );

                Document document = DocumentHome.findByPrimaryKeyWithoutBinaries( nIdDocument );
                DocumentAction action = DocumentActionHome.findByPrimaryKey( nIdAction );

                if ( ( action == null ) || ( action.getFinishDocumentState(  ) == null ) || ( document == null ) ||
                        !DocumentService.getInstance(  )
                                            .isAuthorizedAdminDocument( document.getSpaceId(  ),
                            document.getCodeDocumentType(  ), action.getPermission(  ), user ) )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                try
                {
                    DocumentService.getInstance(  )
                                   .validateDocument( document, user, action.getFinishDocumentState(  ).getId(  ) );
                }
                catch ( DocumentException e )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                IndexationService.addIndexerAction( strIdDocument, DocumentIndexer.INDEXER_NAME,
                    IndexerAction.TASK_MODIFY, IndexationService.ALL_DOCUMENT );

                DocumentIndexerUtils.addIndexerAction( strIdDocument, IndexerAction.TASK_MODIFY,
                    IndexationService.ALL_DOCUMENT );

                return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
            }
            else
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Assign a document to the portlet 
     * @param strIdDocument id document
     * @param strIdPortlet id portlet
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_DOCUMENT_ASSIGN_PORTLET )
    @Produces( MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doAssignDocumentToPortlet( 
        @FormParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument, @FormParam( DocumentRestConstants.PARAMETER_ID_PORTLET )
    String strIdPortlet, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdPortlet ) && StringUtils.isNumeric( strIdPortlet ) &&
                StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdPortlet = Integer.parseInt( strIdPortlet );
            int nIdDocument = Integer.parseInt( strIdDocument );
            PublishingService.getInstance(  ).assign( nIdDocument, nIdPortlet );

            return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Unassign a document to the portlet 
     * @param strIdDocument id document
     * @param strIdPortlet id portlet
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_DOCUMENT_UNASSIGN_PORTLET )
    @Produces( MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doUnassignDocumentToPortlet( 
        @FormParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument, @FormParam( DocumentRestConstants.PARAMETER_ID_PORTLET )
    String strIdPortlet, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdPortlet ) && StringUtils.isNumeric( strIdPortlet ) &&
                StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdPortlet = Integer.parseInt( strIdPortlet );
            int nIdDocument = Integer.parseInt( strIdDocument );
            PublishingService.getInstance(  ).unAssign( nIdDocument, nIdPortlet );

            return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Publish a document
     * @param strIdDocument id document
     * @param strIdPortlet id portlet
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_DOCUMENT_PUBLISH )
    @Produces( MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doPublishDocument( @FormParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument, @FormParam( DocumentRestConstants.PARAMETER_ID_PORTLET )
    String strIdPortlet, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdPortlet ) && StringUtils.isNumeric( strIdPortlet ) &&
                StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdPortlet = Integer.parseInt( strIdPortlet );
            int nIdDocument = Integer.parseInt( strIdDocument );
            PublishingService.getInstance(  ).publish( nIdDocument, nIdPortlet );

            return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Unpublish a document
     * @param strIdDocument id document
     * @param strIdPortlet id portlet
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_DOCUMENT_UNPUBLISH )
    @Produces( MediaType.APPLICATION_XML )
    @Consumes( MediaType.APPLICATION_FORM_URLENCODED )
    public String doUnpublishDocument( @FormParam( DocumentRestConstants.PARAMETER_ID_DOCUMENT )
    String strIdDocument, @FormParam( DocumentRestConstants.PARAMETER_ID_PORTLET )
    String strIdPortlet, @Context
    HttpServletRequest request )
    {
        if ( StringUtils.isNotBlank( strIdPortlet ) && StringUtils.isNumeric( strIdPortlet ) &&
                StringUtils.isNotBlank( strIdDocument ) && StringUtils.isNumeric( strIdDocument ) )
        {
            int nIdPortlet = Integer.parseInt( strIdPortlet );
            int nIdDocument = Integer.parseInt( strIdDocument );
            PublishingService.getInstance(  ).unPublish( nIdDocument, nIdPortlet );

            return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }

    /**
     * Create document
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_CREATE_DOCUMENT )
    @Consumes( MediaType.MULTIPART_FORM_DATA )
    @Produces( {MediaType.TEXT_HTML,
        MediaType.TEXT_XML,
        MediaType.APPLICATION_XML,
        MediaType.APPLICATION_JSON
    } )
    public String doCreateDocument( @Context
    HttpServletRequest request )
    {
        String strResponseXml = StringUtils.EMPTY;
        int nSizeThreshold = AppPropertiesService.getPropertyInt( DocumentRestConstants.PROPERTY_MULTIPART_SIZE_THRESHOLD,
                -1 );
        boolean bActivateNormalizeFileName = Boolean.getBoolean( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTY_MULTIPART_NORMALIZE_FILE_NAME ) );
        String strRequestSizeMax = AppPropertiesService.getProperty( DocumentRestConstants.PROPERTY_MULTIPART_REQUEST_SIZE_MAX );
        long nRequestSizeMax = 0;

        if ( StringUtils.isNotBlank( strRequestSizeMax ) && StringUtils.isNumeric( strRequestSizeMax ) )
        {
            nRequestSizeMax = Long.parseLong( strRequestSizeMax );
        }

        try
        {
            MultipartHttpServletRequest multipartRequest = MultipartUtil.convert( nSizeThreshold, nRequestSizeMax,
                    bActivateNormalizeFileName, request );

            if ( multipartRequest != null )
            {
                strResponseXml = createDocument( multipartRequest );
            }
        }
        catch ( SizeLimitExceededException e )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }
        catch ( FileUploadException e )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }

        return strResponseXml;
    }

    private String createDocument( MultipartHttpServletRequest multipartRequest )
    {
        if ( StringUtils.isNotBlank( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_USER ) ) &&
                StringUtils.isNumeric( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_USER ) ) )
        {
            //Create AdminUser
            int nIdUser = Integer.parseInt( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_USER ) );
            AdminUser user = AdminUserHome.findByPrimaryKey( nIdUser );

            if ( user != null )
            {
                user.setRights( AdminUserHome.getRightsListForUser( nIdUser ) );
                user.setRoles( AdminUserHome.getRolesListForUser( nIdUser ) );

                String strDocumentTypeCode = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_CODE_DOCUMENT_TYPE );

                if ( !DocumentService.getInstance(  )
                                         .isAuthorizedAdminDocument( Integer.parseInt( multipartRequest.getParameter( 
                                    DocumentRestConstants.PARAMETER_ID_SPACE ) ), strDocumentTypeCode,
                            DocumentTypeResourceIdService.PERMISSION_CREATE, user ) )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                Document document = new Document(  );
                document.setCodeDocumentType( strDocumentTypeCode );

                String strError = getDocumentData( multipartRequest, document, multipartRequest.getLocale(  ) );

                if ( strError != null )
                {
                    return strError;
                }

                document.setSpaceId( Integer.parseInt( multipartRequest.getParameter( 
                            DocumentRestConstants.PARAMETER_ID_SPACE ) ) );
                document.setStateId( 1 );
                document.setCreatorId( user.getUserId(  ) );

                try
                {
                    DocumentService.getInstance(  ).createDocument( document, user );
                }
                catch ( DocumentException e )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                // process
                ResourceEnhancer.doCreateResourceAddOn( multipartRequest, DocumentRestConstants.PROPERTY_RESOURCE_TYPE, document.getId(  ) );
            }
        }
        else
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }

        return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
    }

    /**
    * Return the data of a document object
    * @param multipartRequest map of parameters
    * @param document The document object
    * @return data of document object
    */
    public static String getDocumentData( MultipartHttpServletRequest multipartRequest, Document document, Locale locale )
    {
        String strDocumentTitle = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_DOCUMENT_TITLE );
        String strDocumentSummary = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_DOCUMENT_SUMMARY );
        String strDocumentComment = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_DOCUMENT_COMMENT );
        String strDateValidityBegin = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_VALIDITY_BEGIN );
        String strDateValidityEnd = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_VALIDITY_END );
        String strAcceptSiteComments = ( multipartRequest.getParameter(DocumentRestConstants. PARAMETER_ACCEPT_SITE_COMMENTS ) != null )
            ? multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ACCEPT_SITE_COMMENTS ) : "0";
        String strIsModeratedComment = ( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_IS_MODERATED_COMMENT ) != null )
            ? multipartRequest.getParameter( DocumentRestConstants.PARAMETER_IS_MODERATED_COMMENT ) : "0";
        String strIsEmailNotifiedComment = ( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_IS_EMAIL_NOTIFIED_COMMENT ) != null )
            ? multipartRequest.getParameter( DocumentRestConstants.PARAMETER_IS_EMAIL_NOTIFIED_COMMENT ) : "0";
        String strMailingListId = ( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_MAILING_LIST ) != null )
            ? multipartRequest.getParameter( DocumentRestConstants.PARAMETER_MAILING_LIST ) : "0";
        int nMailingListId = Integer.parseInt( strMailingListId );
        String strPageTemplateDocumentId = ( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_PAGE_TEMPLATE_DOCUMENT_ID ) != null )
            ? multipartRequest.getParameter( DocumentRestConstants.PARAMETER_PAGE_TEMPLATE_DOCUMENT_ID ) : "0";
        int nPageTemplateDocumentId = Integer.parseInt( strPageTemplateDocumentId );
        String[] listCategory = multipartRequest.getParameterValues( DocumentRestConstants.PARAMETER_CATEGORY );

        // Check for mandatory value
        if ( strDocumentTitle.trim(  ).equals( "" ) || strDocumentSummary.trim(  ).equals( "" ) )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }

        // Check for illegal character character
        if ( StringUtil.containsHtmlSpecialCharacters( strDocumentTitle ) ||
                StringUtil.containsHtmlSpecialCharacters( strDocumentSummary ) )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }

        DocumentType documentType = DocumentTypeHome.findByPrimaryKey( document.getCodeDocumentType(  ) );
        List<DocumentAttribute> listAttributes = documentType.getAttributes(  );

        for ( DocumentAttribute attribute : listAttributes )
        {
            String strAdminMessage = setAttribute( attribute, document, multipartRequest, locale );

            if ( strAdminMessage != null )
            {
                return strAdminMessage;
            }
        }

        Timestamp dateValidityBegin = null;
        Timestamp dateValidityEnd = null;

        if ( ( strDateValidityBegin != null ) && !strDateValidityBegin.equals( "" ) )
        {
            Date dateBegin = DateUtil.formatDateLongYear( strDateValidityBegin, locale );

            if ( ( dateBegin == null ) )
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }

            dateValidityBegin = new Timestamp( dateBegin.getTime(  ) );

            if ( dateValidityBegin.before( new Timestamp( 0 ) ) )
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }
        }

        if ( ( strDateValidityEnd != null ) && !strDateValidityEnd.equals( "" ) )
        {
            Date dateEnd = DateUtil.formatDateLongYear( strDateValidityEnd, locale );

            if ( ( dateEnd == null ) )
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }

            dateValidityEnd = new Timestamp( dateEnd.getTime(  ) );

            if ( dateValidityEnd.before( new Timestamp( 0 ) ) )
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }
        }

        //validate period (dateEnd > dateBegin )
        if ( ( dateValidityBegin != null ) && ( dateValidityEnd != null ) )
        {
            if ( dateValidityEnd.before( dateValidityBegin ) )
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }
        }

        document.setTitle( strDocumentTitle );
        document.setSummary( strDocumentSummary );
        document.setComment( strDocumentComment );
        document.setDateValidityBegin( dateValidityBegin );
        document.setDateValidityEnd( dateValidityEnd );
        document.setAcceptSiteComments( Integer.parseInt( strAcceptSiteComments ) );
        document.setIsModeratedComment( Integer.parseInt( strIsModeratedComment ) );
        document.setIsEmailNotifiedComment( Integer.parseInt( strIsEmailNotifiedComment ) );
        document.setMailingListId( nMailingListId );
        document.setPageTemplateDocumentId( nPageTemplateDocumentId );

        MetadataHandler hMetadata = documentType.metadataHandler(  );

        if ( hMetadata != null )
        {
            document.setXmlMetadata( hMetadata.getXmlMetadata( multipartRequest.getParameterMap(  ) ) );
        }

        document.setAttributes( listAttributes );

        //Categories
        List<Category> listCategories = new ArrayList<Category>(  );

        if ( listCategory != null )
        {
            for ( String strIdCategory : listCategory )
            {
                listCategories.add( CategoryHome.find( Integer.parseInt( strIdCategory ) ) );
            }
        }

        document.setCategories( listCategories );

        return null; // No error
    }

    /**
     * Update the specify attribute with the parameters map     *
     * @param attribute The {@link DocumentAttribute} to update
     * @param document The {@link Document}
     * @param mRequest The multipart http request
     * @return reponse xml
     */
    private static String setAttribute( DocumentAttribute attribute, Document document,
        MultipartHttpServletRequest multipartRequest, Locale locale )
    {
        String strParameterStringValue = multipartRequest.getParameter( attribute.getCode(  ) );
        FileItem fileParameterBinaryValue = multipartRequest.getFile( attribute.getCode(  ) );
        String strIsUpdatable = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ATTRIBUTE_UPDATE + attribute.getCode(  ) );
        boolean bIsUpdatable = ( ( strIsUpdatable == null ) || strIsUpdatable.equals( "" ) ) ? false : true;

        if ( attribute.isRequired(  ) &&
                ( StringUtils.isBlank( strParameterStringValue ) && ( fileParameterBinaryValue == null ) ) )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }

        if ( strParameterStringValue != null ) // If the field is a string
        {
            // Check for specific attribute validation
            AttributeManager manager = AttributeService.getInstance(  ).getManager( attribute.getCodeAttributeType(  ) );
            String strValidationErrorMessage = manager.validateValue( attribute.getId(  ), strParameterStringValue,
                    locale );

            if ( strValidationErrorMessage != null )
            {
                //String[] listArguments = { attribute.getName(  ), strValidationErrorMessage };
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }

            attribute.setTextValue( strParameterStringValue );
        }
        else if ( fileParameterBinaryValue != null ) // If the field is a file
        {
            attribute.setBinary( true );

            String strContentType = fileParameterBinaryValue.getContentType(  );
            byte[] bytes = fileParameterBinaryValue.get(  );
            String strFileName = fileParameterBinaryValue.getName(  );

            if ( !bIsUpdatable )
            {
                // there is no new value then take the old file value
                DocumentAttribute oldAttribute = document.getAttribute( attribute.getCode(  ) );

                if ( ( oldAttribute != null ) && ( oldAttribute.getBinaryValue(  ) != null ) &&
                        ( oldAttribute.getBinaryValue(  ).length > 0 ) )
                {
                    bytes = oldAttribute.getBinaryValue(  );
                    strContentType = oldAttribute.getValueContentType(  );
                    strFileName = oldAttribute.getTextValue(  );
                }
            }

            // Check for mandatory value
            if ( attribute.isRequired(  ) && ( bytes.length == 0 ) )
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }

            // Check for specific attribute validation
            AttributeManager manager = AttributeService.getInstance(  ).getManager( attribute.getCodeAttributeType(  ) );
            String strValidationErrorMessage = manager.validateValue( attribute.getId(  ), strFileName, locale );

            if ( strValidationErrorMessage != null )
            {
                //String[] listArguments = { attribute.getName(  ), strValidationErrorMessage };
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }

            attribute.setBinaryValue( bytes );
            attribute.setValueContentType( strContentType );
            attribute.setTextValue( strFileName );
        }

        return null;
    }

    /**
     * Modify document
     * @param request request
     * @return response xml
     */
    @POST
    @Path( DocumentRestConstants.PATH_MODIFY_DOCUMENT )
    @Consumes( MediaType.MULTIPART_FORM_DATA )
    @Produces( {MediaType.TEXT_HTML,
        MediaType.TEXT_XML,
        MediaType.APPLICATION_XML,
        MediaType.APPLICATION_JSON
    } )
    public String doModifyDocument( @Context
    HttpServletRequest request )
    {
        String strResponseXml = StringUtils.EMPTY;
        int nSizeThreshold = AppPropertiesService.getPropertyInt( DocumentRestConstants.PROPERTY_MULTIPART_SIZE_THRESHOLD,
                -1 );
        boolean bActivateNormalizeFileName = Boolean.getBoolean( AppPropertiesService.getProperty( 
                    DocumentRestConstants.PROPERTY_MULTIPART_NORMALIZE_FILE_NAME ) );
        String strRequestSizeMax = AppPropertiesService.getProperty( DocumentRestConstants.PROPERTY_MULTIPART_REQUEST_SIZE_MAX );
        long nRequestSizeMax = 0;

        if ( StringUtils.isNotBlank( strRequestSizeMax ) && StringUtils.isNumeric( strRequestSizeMax ) )
        {
            nRequestSizeMax = Long.parseLong( strRequestSizeMax );
        }

        try
        {
            MultipartHttpServletRequest multipartRequest = MultipartUtil.convert( nSizeThreshold, nRequestSizeMax,
                    bActivateNormalizeFileName, request );

            if ( multipartRequest != null )
            {
                strResponseXml = modifyDocument( multipartRequest );
            }
        }
        catch ( SizeLimitExceededException e )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }
        catch ( FileUploadException e )
        {
            return ResponseActionBuilderXml.getFailureResponseActionXML(  );
        }

        return strResponseXml;
    }

    private String modifyDocument( MultipartHttpServletRequest multipartRequest )
    {
        if ( StringUtils.isNotBlank( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_USER ) ) &&
                StringUtils.isNumeric( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_USER ) ) )
        {
            //Create AdminUser
            int nIdUser = Integer.parseInt( multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_USER ) );
            AdminUser user = AdminUserHome.findByPrimaryKey( nIdUser );

            if ( user != null )
            {
                user.setRights( AdminUserHome.getRightsListForUser( nIdUser ) );
                user.setRoles( AdminUserHome.getRolesListForUser( nIdUser ) );

                String strDocumentId = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_ID_DOCUMENT );

                Document document = DocumentHome.findByPrimaryKeyWithoutBinaries( Integer.parseInt( strDocumentId ) );

                if ( !DocumentService.getInstance(  )
                                         .isAuthorizedAdminDocument( document.getSpaceId(  ),
                            document.getCodeDocumentType(  ), DocumentTypeResourceIdService.PERMISSION_MODIFY, user ) )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                String strError = getDocumentData( multipartRequest, document, multipartRequest.getLocale(  ) );

                if ( strError != null )
                {
                    return strError;
                }

                try
                {
                    DocumentService.getInstance(  ).modifyDocument( document, user );
                }
                catch ( DocumentException e )
                {
                    return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                }

                String strStateId = multipartRequest.getParameter( DocumentRestConstants.PARAMETER_STATE_ID );

                if ( StringUtils.isNotBlank( strStateId ) && StringUtils.isNumeric( strStateId ) )
                {
                    int nStateId = Integer.parseInt( strStateId );

                    try
                    {
                        DocumentService.getInstance(  ).changeDocumentState( document, user, nStateId );
                    }
                    catch ( DocumentException e )
                    {
                        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
                    }
                }

                return ResponseActionBuilderXml.getSuccessResponseActionXML(  );
            }
            else
            {
                return ResponseActionBuilderXml.getFailureResponseActionXML(  );
            }
        }

        return ResponseActionBuilderXml.getFailureResponseActionXML(  );
    }
}
