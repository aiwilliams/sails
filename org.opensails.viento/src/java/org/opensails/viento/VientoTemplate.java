package org.opensails.viento;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.opensails.viento.ast.Template;
import org.opensails.viento.parser.ParseException;
import org.opensails.viento.parser.Parser;

public class VientoTemplate {
    protected InputStream stream;
    protected Template template;

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
        getTemplateNode().evaluate(templateBinding, output);
        return output;
    }
    
    public Template getAst() {
    	return getTemplateNode();
    }

    protected Template getTemplateNode() {
        if (template == null) {
            Parser parser = new Parser(stream);
            try {
                template = parser.template();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return template;
    }
}
