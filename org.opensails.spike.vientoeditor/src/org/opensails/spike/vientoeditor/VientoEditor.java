package org.opensails.spike.vientoeditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.Reconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.opensails.viento.ast.BodyNode;
import org.opensails.viento.ast.Template;
import org.opensails.viento.ast.Text;

public class VientoEditor extends AbstractDecoratedTextEditor {
	Color red, blue, white;
	
	@Override protected void initializeEditor() {
		setSourceViewerConfiguration(new SourceViewerConfiguration() {
			@Override public IReconciler getReconciler(ISourceViewer sourceViewer) {
				Reconciler reconciler = new Reconciler();
				reconciler.setDelay(10);
				reconciler.setIsIncrementalReconciler(false);
				reconciler.setReconcilingStrategy(new VientoReconcilingStrategy(VientoEditor.this), IDocument.DEFAULT_CONTENT_TYPE);
				return reconciler;
			}
		});
	}
	
	@Override public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		red = new Color(getSite().getShell().getDisplay(), 255, 0, 0);
		blue = new Color(getSite().getShell().getDisplay(), 0, 0, 255);
		white = new Color(getSite().getShell().getDisplay(), 255, 255, 255);
	}
	
	@Override public void dispose() {
		super.dispose();
		white.dispose();
		red.dispose();
		blue.dispose();
	}
	
	public void reconciled(Template template) {
		final List<StyleRange> ranges = new ArrayList<StyleRange>();
		for (BodyNode node : template.nodes) {
			int offset = node.startOffset();
			int length = node.length() + 1;
			System.out.println("offset: " + offset + " lenth: " + length);
			ranges.add(new StyleRange(offset, length, node instanceof Text ? red : blue, white));
		}
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				getSourceViewer().getTextWidget().setStyleRanges((StyleRange[]) ranges.toArray(new StyleRange[ranges.size()]));
			}
		});
	}
}
