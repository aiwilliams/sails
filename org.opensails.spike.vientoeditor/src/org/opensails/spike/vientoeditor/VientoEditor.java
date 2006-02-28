package org.opensails.spike.vientoeditor;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.eclipse.ui.texteditor.ContentAssistAction;
import org.eclipse.ui.texteditor.ITextEditorActionDefinitionIds;
import org.opensails.viento.ast.Template;

public class VientoEditor extends AbstractDecoratedTextEditor {
	protected VientoHighlighter highlighter;
	protected HardCodedHighlightingConfiguration highlightingConfiguration;
	protected VientoReconcilingStrategy reconcilingStrategy;

	@Override protected void initializeEditor() {
		reconcilingStrategy = new VientoReconcilingStrategy(this);
		setSourceViewerConfiguration(new SourceViewerConfiguration() {
			@Override public IReconciler getReconciler(ISourceViewer sourceViewer) {
				MonoReconciler reconciler = new MonoReconciler(reconcilingStrategy, false);
				reconciler.setDelay(10);
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
		highlightingConfiguration = new HardCodedHighlightingConfiguration(getSite().getShell().getDisplay());
		highlighter = new VientoHighlighter(highlightingConfiguration);
		// doesn't seem to get called by the framework
		reconcilingStrategy.initialReconcile();
	}

	@Override public void dispose() {
		highlightingConfiguration.dispose();
		super.dispose();
	}

	public void reconciled(Template template) {
		/*
		 * TODO: Consider document partitioning.
		 */
		
		if (getSite().getShell().isDisposed()) return;

		final StyleRange[] ranges = highlighter.rangesFor(template);
		getSite().getShell().getDisplay().syncExec(new Runnable() {
			public void run() {
				getSourceViewer().getTextWidget().setStyleRanges(ranges);
			}
		});
	}
}
