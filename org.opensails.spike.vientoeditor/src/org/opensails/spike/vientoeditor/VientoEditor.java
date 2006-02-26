package org.opensails.spike.vientoeditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
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
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.opensails.viento.ast.Template;

public class VientoEditor extends AbstractDecoratedTextEditor {
	protected VientoHighlighter highlighter = new VientoHighlighter();
	Color red, blue, white;

	@Override protected void initializeEditor() {
		setSourceViewerConfiguration(new SourceViewerConfiguration() {
			@Override public IReconciler getReconciler(ISourceViewer sourceViewer) {
				Reconciler reconciler = new Reconciler();
				reconciler.setDelay(0);
				reconciler.setIsIncrementalReconciler(false);
				reconciler.setReconcilingStrategy(new VientoReconcilingStrategy(VientoEditor.this), IDocument.DEFAULT_CONTENT_TYPE);
				return reconciler;
			}

			@Override public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
				ContentAssistant assistant = new ContentAssistant();
				assistant.setContentAssistProcessor(new VientoContentAssistProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
				return assistant;
			}
		});
	}

	@Override protected void createActions() {
		super.createActions();

		IAction action = new ContentAssistAction(new EmptyResourceBundle(), "ContentAssistProposal.", this);
		action.setActionDefinitionId(ITextEditorActionDefinitionIds.CONTENT_ASSIST_PROPOSALS);
		setAction("ContentAssistProposal", action);
		markAsStateDependentAction("ContentAssistProposal", true);
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
		/*
		 * TODO: Consider document partitioning.
		 */
		
		final StyleRange[] ranges = highlighter.rangesFor(template);
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				getSourceViewer().getTextWidget().setStyleRanges(ranges);
			}
		});
	}
}
