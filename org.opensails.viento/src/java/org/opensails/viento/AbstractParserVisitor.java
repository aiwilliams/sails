package org.opensails.viento;

import org.opensails.viento.parser.ASTAnd;
import org.opensails.viento.parser.ASTArguments;
import org.opensails.viento.parser.ASTBlock;
import org.opensails.viento.parser.ASTBody;
import org.opensails.viento.parser.ASTBoolean;
import org.opensails.viento.parser.ASTBooleanExpression;
import org.opensails.viento.parser.ASTCall;
import org.opensails.viento.parser.ASTExpression;
import org.opensails.viento.parser.ASTList;
import org.opensails.viento.parser.ASTMap;
import org.opensails.viento.parser.ASTMapEntry;
import org.opensails.viento.parser.ASTName;
import org.opensails.viento.parser.ASTNot;
import org.opensails.viento.parser.ASTNull;
import org.opensails.viento.parser.ASTNumber;
import org.opensails.viento.parser.ASTOr;
import org.opensails.viento.parser.ASTStatement;
import org.opensails.viento.parser.ASTString;
import org.opensails.viento.parser.ASTStringBlock;
import org.opensails.viento.parser.ASTSymbol;
import org.opensails.viento.parser.ASTTemplate;
import org.opensails.viento.parser.ASTText;
import org.opensails.viento.parser.ParserVisitor;
import org.opensails.viento.parser.SimpleNode;

public class AbstractParserVisitor implements ParserVisitor {
	
	public void visit(SimpleNode node) {
	}

	public void visit(ASTTemplate node) {
	}

	public void visit(ASTText node) {
	}

	public void visit(ASTStatement node) {
	}

	public void visit(ASTCall node) {
	}

	public void visit(ASTName node) {
	}

	public void visit(ASTExpression node) {
	}

	public void visit(ASTArguments node) {
	}

	public void visit(ASTNumber node) {
	}

	public void visit(ASTBoolean node) {
	}
	
	public void visit(ASTBody node) {
	}

	public void visit(ASTBlock node) {
	}

    public void visit(ASTString node) {
	}
    
    public void visit(ASTList node) {
    }
    
    public void visit(ASTMap node) {
    }
    
    public void visit(ASTMapEntry node) {
    }
    
    public void visit(ASTNull node) {
    }
	
    public void visit(ASTSymbol node) {
    }

    public void visit(ASTAnd node) {
    }

    public void visit(ASTOr node) {
    }

    public void visit(ASTNot node) {
    }

    public void visit(ASTBooleanExpression node) {
    }
    
    public void visit(ASTStringBlock node) {
    }

	public Object visit(SimpleNode node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTTemplate node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTText node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTStatement node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTCall node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTName node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTArguments node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTExpression node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTNumber node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTBoolean node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTBody node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTBlock node, Object data) {
		visit(node);
		return data;
	}
	
	public Object visit(ASTString node, Object data) {
	    visit(node);
	    return data;
	}
	
	public Object visit(ASTList node, Object data) {
	    visit(node);
	    return data;
	}
	
	public Object visit(ASTMap node, Object data) {
	    visit(node);
	    return data;
	}
	
	public Object visit(ASTMapEntry node, Object data) {
	    visit(node);
	    return data;
	}
	
	public Object visit(ASTNull node, Object data) {
		visit(node);
		return data;
	}
	
	public Object visit(ASTSymbol node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTAnd node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTOr node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTNot node, Object data) {
		visit(node);
		return data;
	}

	public Object visit(ASTBooleanExpression node, Object data) {
		visit(node);
		return data;
	}
	
	public Object visit(ASTStringBlock node, Object data) {
		visit(node);
		return data;
	}
}
