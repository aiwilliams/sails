package org.opensails.viento;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.opensails.viento.parser.ParseException;
import org.opensails.viento.parser.Parser;
import org.opensails.viento.parser.SimpleNode;

public class VientoTemplate {
    protected InputStream stream;
    protected SimpleNode template;

    public VientoTemplate(InputStream inputStream) {
        this.stream = inputStream;
    }

    public VientoTemplate(String input) {
        this(new ByteArrayInputStream(input.getBytes()));
    }

    public String render(Binding templateBinding) {
        return render(new StringBuilder(), templateBinding).toString();
    }

    public StringBuilder render(StringBuilder output, Binding templateBinding) {
        VientoVisitor visitor = new VientoVisitor(output, templateBinding);
        getTemplateNode().childrenAccept(visitor, null);
        return output;
    }

    protected SimpleNode getTemplateNode() {
        if (template == null) {
            Parser parser = new Parser(stream);
            try {
                template = parser.Template();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return template;
    }
}
