package org.opensails.viento;

import org.opensails.viento.parser.ASTAnd;
import org.opensails.viento.parser.ASTArguments;
import org.opensails.viento.parser.ASTBlock;
import org.opensails.viento.parser.ASTBody;
import org.opensails.viento.parser.ASTBoolean;
import org.opensails.viento.parser.ASTCall;
import org.opensails.viento.parser.ASTEqual;
import org.opensails.viento.parser.ASTExpression;
import org.opensails.viento.parser.ASTGreaterThan;
import org.opensails.viento.parser.ASTGreaterThanOrEqual;
import org.opensails.viento.parser.ASTLeftHandExpression;
import org.opensails.viento.parser.ASTLessThan;
import org.opensails.viento.parser.ASTLessThanOrEqual;
import org.opensails.viento.parser.ASTList;
import org.opensails.viento.parser.ASTMap;
import org.opensails.viento.parser.ASTMapEntry;
import org.opensails.viento.parser.ASTName;
import org.opensails.viento.parser.ASTNot;
import org.opensails.viento.parser.ASTNotEqual;
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
	
	public void visit(ASTAnd node) {
    }

	public Object visit(ASTAnd node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTArguments node) {
	}

	public Object visit(ASTArguments node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTBlock node) {
	}

	public Object visit(ASTBlock node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTBody node) {
	}

	public Object visit(ASTBody node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTBoolean node) {
	}

	public Object visit(ASTBoolean node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(ASTCall node) {
	}

	public Object visit(ASTCall node, Object data) {
		visit(node);
		return data;
	}

    public void visit(ASTEqual node) {
    }
    
    public Object visit(ASTEqual node, Object data) {
		visit(node);
		return data;
	}
    
    public void visit(ASTExpression node) {
	}
    
    public Object visit(ASTExpression node, Object data) {
		visit(node);
		return data;
	}
    
    public void visit(ASTGreaterThan node) {
	}
    
    public Object visit(ASTGreaterThan node, Object data) {
		visit(node);
		return data;
	}
    
    public void visit(ASTGreaterThanOrEqual node) {
	}
	
    public Object visit(ASTGreaterThanOrEqual node, Object data) {
		visit(node);
		return data;
	}

    public void visit(ASTLeftHandExpression node) {
    }

    public Object visit(ASTLeftHandExpression node, Object data) {
		visit(node);
		return data;
	}

    public void visit(ASTLessThan node) {
	}

    public Object visit(ASTLessThan node, Object data) {
		visit(node);
		return data;
	}
    
    public void visit(ASTLessThanOrEqual node) {
	}
    
    public Object visit(ASTLessThanOrEqual node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTList node) {
    }

	public Object visit(ASTList node, Object data) {
	    visit(node);
	    return data;
	}

	public void visit(ASTMap node) {
    }

	public Object visit(ASTMap node, Object data) {
	    visit(node);
	    return data;
	}

	public void visit(ASTMapEntry node) {
    }

	public Object visit(ASTMapEntry node, Object data) {
	    visit(node);
	    return data;
	}

	public void visit(ASTName node) {
	}

	public Object visit(ASTName node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTNot node) {
    }

	public Object visit(ASTNot node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTNotEqual node) {
    }

	public Object visit(ASTNotEqual node, Object data) {
    	visit(node);
    	return data;
    }
	
	public void visit(ASTNull node) {
    }
	
	public Object visit(ASTNull node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(ASTNumber node) {
	}
	
	public Object visit(ASTNumber node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(ASTOr node) {
    }
	
	public Object visit(ASTOr node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTStatement node) {
	}

	public Object visit(ASTStatement node, Object data) {
		visit(node);
		return data;
	}

	public void visit(ASTString node) {
	}

	public Object visit(ASTString node, Object data) {
	    visit(node);
	    return data;
	}
	
	public void visit(ASTStringBlock node) {
    }
	
	public Object visit(ASTStringBlock node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(ASTSymbol node) {
    }
	
	public Object visit(ASTSymbol node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(ASTTemplate node) {
	}
	
	public Object visit(ASTTemplate node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(ASTText node) {
	}
	
	public Object visit(ASTText node, Object data) {
		visit(node);
		return data;
	}
	
	public void visit(SimpleNode node) {
	}
	
	public Object visit(SimpleNode node, Object data) {
		visit(node);
		return data;
	}
}
