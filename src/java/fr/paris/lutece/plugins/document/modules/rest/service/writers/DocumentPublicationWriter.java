/**
 *
 */
package fr.paris.lutece.plugins.document.modules.rest.service.writers;

import fr.paris.lutece.plugins.document.business.publication.DocumentPublication;
import fr.paris.lutece.plugins.rest.service.writers.AbstractWriter;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;


/**
 * DocumentPublicationWriter
 *
 */
@Provider
@Produces( {MediaType.APPLICATION_XML,
    MediaType.APPLICATION_JSON
} )
public class DocumentPublicationWriter extends AbstractWriter<DocumentPublication>
{
	/**
     * {@inheritDoc}
     */
    public boolean isWriteable( Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType )
    {
        // Ensure that we're handling only List<Directory> objects.
        boolean isWritable = false;

        if ( DocumentPublication.class.equals( genericType ) )
        {
            isWritable = true;
        }

        if ( List.class.isAssignableFrom( type ) && genericType instanceof ParameterizedType )
        {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArgs = ( parameterizedType.getActualTypeArguments(  ) );
            isWritable = ( ( actualTypeArgs.length == 1 ) && actualTypeArgs[0].equals( DocumentPublication.class ) );
        }

        return isWritable;
    }
}
