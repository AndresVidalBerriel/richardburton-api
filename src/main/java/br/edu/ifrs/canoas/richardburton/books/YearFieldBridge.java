package br.edu.ifrs.canoas.richardburton.books;

import java.time.Year;
import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;

public class YearFieldBridge implements FieldBridge {

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {

        luceneOptions.addFieldToDocument(name, Integer.toString(((Year) value).getValue()),
                document);

    }


}
