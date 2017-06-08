package programacion.mundo;

/**
 * Copyright 2011-2013 SCENERGY S.C.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
import java.io.*;
 
/**
 * Métodos de utilería relacionados con verificaciones.
 */
public class Serialization
{
 
  /**
   * Copiar un objeto a través de mecanismos de serialización.
   */
  @SuppressWarnings( "unchecked" )
  public static <T> T copy( T source )
  {
    try
    {
      ByteArrayOutputStream ostream = new ByteArrayOutputStream();
      ObjectOutputStream oostream = new ObjectOutputStream( ostream );
   
      oostream.writeObject( source );
      oostream.flush();
   
      byte[] bytes = ostream.toByteArray();
       
      InputStream istream = new ByteArrayInputStream( bytes );
      ObjectInputStream oistream = new ObjectInputStream( istream );
       
      return ( T )oistream.readObject();
    }
    catch( RuntimeException e )
      { throw e; }
    catch( Exception e )
      { throw new IllegalArgumentException( source.getClass().getName(), e ); }
  }
 
}