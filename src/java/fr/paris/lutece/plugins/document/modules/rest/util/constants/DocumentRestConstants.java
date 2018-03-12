/*
 * Copyright (c) 2002-2018, Mairie de Paris
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
package fr.paris.lutece.plugins.document.modules.rest.util.constants;

/**
 *
 * DocumentRestConstants
 *
 */
public class DocumentRestConstants
{
    // CONSTANTS
    public static final String SLASH = "/";
    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILURE";

    // PATHS
    public static final String PATH_WADL = "wadl";
    public static final String PATH_GET_USER_SPACES = "getuserspaces/{id_user}";
    public static final String PATH_GET_SPACE = "getspace/{id_space}";
    public static final String PATH_GET_SPACES_ALLOWING_DOCUMENT_CREATION_BY_CODE_DOCUMENT_TYPE = "getspacesbydocumenttype/{code_document_type}";
    public static final String PATH_GET_ATTRIBUTE_BY_DOCUMENT_TYPE = "getdocumenttypeattribute/{code_document_type}";
    public static final String PATH_GET_DOCUMENTS_LIST_BY_SPACE = "getdocumentslistbyspace/{id_space}";
    public static final String PATH_GET_DOCUMENT = "getdocument/{id_document}";
    public static final String PATH_MANAGE = "manage/";
    public static final String PATH_CREATE_GET_FIELDS = "getcreationdocumentfields/{code_document_type}";
    public static final String PATH_GET_LIST_DOCUMENT_TYPE = "getdocumenttypelist/";
    public static final String PATH_CREATE_DOCUMENT = "docreatedocument/";
    public static final String PATH_MODIFY_DOCUMENT = "domodifydocument/";
    public static final String PATH_REMOVE_DOCUMENT = "doremovedocument/{id_document}";
    public static final String PATH_PORTLETS_DOCUMENT = "getportletstoassigndocument/{id_document}";
    public static final String PATH_PORTLETS_LIST_DOCUMENT = "getportletslisttoassigndocument/{id_document}";
    public static final String PATH_PORTLETS_DOCUMENT_ASSIGNED = "getportletsdocumentassigned/{id_document}";
    public static final String PATH_DOCUMENT_SUBMIT = "dosubmitdocumenttovalidate/";
    public static final String PATH_DOCUMENT_VALIDATE = "dovalidatedocument/";
    public static final String PATH_DOCUMENT_ASSIGN_PORTLET = "doassigndocumentportlet/";
    public static final String PATH_DOCUMENT_UNASSIGN_PORTLET = "dounassigndocumentportlet/";
    public static final String PATH_DOCUMENT_PUBLISH = "dopublishdocument/";
    public static final String PATH_DOCUMENT_UNPUBLISH = "dounpublishdocument/";

    // TAGS
    public static final String TAG_RESPONSE = "Response";
    public static final String TAG_STATUS = "Status";
    public static final String TAG_DOCUMENTS = "Documents";
    public static final String TAG_DOCUMENT = "Document";
    public static final String TAG_ID_DOCUMENT = "Id-document";
    public static final String TAG_ID_SPACE = "Id_space";
    public static final String TAG_PUBLISHED_STATUS = "Published_status";
    public static final String TAG_MAILING_LIST_ID = "Mailing_List_Id";
    public static final String TAG_PAGE_TEMPLATE_DOCUMENT_ID = "Page_Template_Document_Id";
    public static final String TAG_STATE_ID = "State_Id";
    public static final String TAG_IS_NOTIFIED_COMMENT = "Is_Email_Notified_Comment";
    public static final String TAG_IS_MODERATED_COMMENT = "Is_Moderated_Comment";
    public static final String TAG_COMMENT = "Comment";
    public static final String TAG_SPACES = "spaces";
    public static final String TAG_SPACE = "space";
    public static final String TAG_SPACE_ID = "space-id";
    public static final String TAG_SPACE_ID_PARENT = "space-id-parent";
    public static final String TAG_SPACE_NAME = "name";
    public static final String TAG_SPACE_DESCRIPTION = "description";
    public static final String TAG_SPACE_VIEW_TYPE = "view-type";
    public static final String TAG_SPACE_ID_ICON = "space-id-icon";
    public static final String TAG_SPACE_ICON_URL = "space-icon-url";
    public static final String TAG_SPACE_ALLOWED_DOCUMENT_TYPES = "space-allowed-document-types";
    public static final String TAG_SPACE_DOCUMENT_TYPE = "document-type";
    public static final String TAG_SPACE_DOCUMENT_CREATION_ALLOWED = "space-document-creation-allowed";
    public static final String TAG_SPACE_WORKGROUP = "space-workgroup";
    public static final String TAG_DOCUMENT_TYPES = "document_types";
    public static final String TAG_DOCUMENT_TYPE = "document_type";
    public static final String TAG_CODE_DOCUMENT_TYPE = "code_document_type";
    public static final String TAG_DOCUMENT_TYPE_NAME = "document_type_name";
    public static final String TAG_DOCUMENT_TYPE_DESCRIPTION = "description";
    public static final String TAG_DOCUMENT_TYPE_THUMBNAIL_ATTRIBUTE_ID = "thumbnail_attr_id";
    public static final String TAG_DOCUMENT_TYPE_DEFAULT_THUMBNAIL_URL = "default_thumbnail_url";
    public static final String TAG_DOCUMENT_TYPE_METADATA_HANDLER = "metadata_handler";
    public static final String TAG_ATTRIBUTE = "attribute";
    public static final String TAG_ATTRIBUTES = "attributes";
    public static final String TAG_ATTRIBUTE_ID = "id_document_attr";
    public static final String TAG_CODE_ATTRIBUTE_TYPE = "code_attr_type";
    public static final String TAG_CODE_ATTRIBUTE = "code";
    public static final String TAG_DOCUMENT_TYPE_ATTRIBUTE_NAME = "document_type_attr_name";
    public static final String TAG_ATTRIBUTE_DESCRIPTION = "description";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM = "form";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM_MAINFIELDS = "main-fields";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM_DUBLINCORE_FIELDS = "dublincore-fields";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM_ATTRIBUTS_FIELDS = "attributs-fields";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM_ATTRIBUT = "attribut";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM_PARAMETER_ATTRIBUT = "parameter-attribut";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_FORM_PARAMETER_ATTRIBUT_VALUE = "parameter-attribut-value";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_TEXT = "text";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_TEXTAREA = "textarea";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_RADIO = "radio";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_RADIO_VALUE = "radio_value";
    public static final String TAG_CREATE_DOCUMENT_FIELDS_CHECKBOX = "checkbox";
    public static final String TAG_REF_ITEMS = "ref-items";
    public static final String TAG_REF_ITEM = "ref-item";
    public static final String TAG_DOCUMENTS_PUBLICATION = "documents-publication";
    public static final String TAG_DOCUMENT_PUBLICATION = "document-publication";

    // MAP ATTRIBUTS
    public static final String ATTRIBUTS_PORTLET_ID = "portlet-id";
    public static final String ATTRIBUTS_STATUS = "status";
    public static final String ATTRIBUTS_NAME = "name";
    public static final String ATTRIBUTS_TYPE = "type";
    public static final String ATTRIBUTS_MAXLENGTH = "maxlength";
    public static final String ATTRIBUTS_SITE = "size";
    public static final String ATTRIBUTS_ISREQUIRED = "isRequired";
    public static final String ATTRIBUTS_CODE = "code";
    public static final String ATTRIBUTS_VALUE = "value";
    public static final String ATTRIBUTS_ROWS = "rows";
    public static final String ATTRIBUTS_COLS = "cols";

    // PARAMETERS
    public static final String PARAMETER_ID_USER = "id_user";
    public static final String PARAMETER_ID_SPACE = "id_space";
    public static final String PARAMETER_ID_DOCUMENT = "id_document";
    public static final String PARAMETER_ID_ACTION = "id_action";
    public static final String PARAMETER_ID_PORTLET = "id_portlet";
    public static final String PARAMETER_CODE_DOCUMENT_TYPE = "code_document_type";
    public static final String PARAMETER_STATE_ID = "id_state";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_TITLE = "dc_title";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_CREATOR = "dc_creator";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_SUBJECT = "dc_subject";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_DESCRIPTION = "dc_description";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_PUBLISHER = "dc_publisher";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_CONTRIBUTOR = "dc_contributor";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_DATE = "dc_date";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_TYPE = "dc_type";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_FORMAT = "dc_format";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_IDENTIFIER = "dc_identifier";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_SOURCE = "dc_source";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_LANGUAGE = "dc_language";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_RELATION = "dc_relation";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_COVERAGE = "dc_coverage";
    public static final String PARAMETER_DUBLIN_CORE_META_DATA_RIGHTS = "dc_rights";
    public static final String PARAMETER_DOCUMENT_TITLE = "document_title";
    public static final String PARAMETER_DOCUMENT_SUMMARY = "document_summary";
    public static final String PARAMETER_DOCUMENT_COMMENT = "document_comment";
    public static final String PARAMETER_VALIDITY_BEGIN = "document_validity_begin";
    public static final String PARAMETER_VALIDITY_END = "document_validity_end";
    public static final String PARAMETER_ACCEPT_SITE_COMMENTS = "accept_site_comments";
    public static final String PARAMETER_IS_MODERATED_COMMENT = "is_moderated_comment";
    public static final String PARAMETER_IS_EMAIL_NOTIFIED_COMMENT = "is_email_notified_comment";
    public static final String PARAMETER_MAILING_LIST = "mailinglists";
    public static final String PARAMETER_PAGE_TEMPLATE_DOCUMENT_ID = "page_template_id";
    public static final String PARAMETER_CATEGORY = "category_id";
    public static final String PARAMETER_ATTRIBUTE_UPDATE = "update_";

    // PROPERTIES
    public static final String PROPERTY_MULTIPART_SIZE_THRESHOLD = "document-rest.multipart.sizeThreshold";
    public static final String PROPERTY_MULTIPART_REQUEST_SIZE_MAX = "document-rest.multipart.requestSizeMax";
    public static final String PROPERTY_MULTIPART_NORMALIZE_FILE_NAME = "document-rest.multipart.activateNormalizeFileName";
    public static final String PROPERTY_RESOURCE_TYPE = "document";
    public static final String PROPERTIES_XML_HEADER = "document-rest.xml.header";

    // MESSAGES

    // MARKS
    public static final String MARK_BASE_URL = "base_url";

    // TEMPLATES
    public static final String TEMPLATE_WADL = "admin/plugins/document/modules/rest/wadl.xml";

    /**
     * Private constructor
     */
    private DocumentRestConstants( )
    {
    }
}
