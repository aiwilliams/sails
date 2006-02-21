package org.opensails.spike.vientoeditor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;
import org.opensails.viento.VientoTemplate;
import org.opensails.viento.ast.Template;

public class VientoReconcilingStrategy implements IReconcilingStrategy {
	private IDocument document;
	private final VientoEditor editor;

	public VientoReconcilingStrategy(VientoEditor editor) {
		this.editor = editor;
	}

	public void reconcile(IRegion partition) {
		Template ast = null;
		try {
			ast = new VientoTemplate(document.get()).getAst();
		} catch (Throwable e) {
		}
		if (ast != null)
			editor.reconciled(ast);
	}

	public void reconcile(DirtyRegion dirtyRegion, IRegion subRegion) {}

	public void setDocument(IDocument document) {
		this.document = document;
	}
}