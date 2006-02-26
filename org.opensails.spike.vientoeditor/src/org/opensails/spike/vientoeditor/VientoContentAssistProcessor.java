/**
 * 
 */
package org.opensails.spike.vientoeditor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

public class VientoContentAssistProcessor implements IContentAssistProcessor {
	public IContextInformationValidator getContextInformationValidator() {
		return null;
	}

	public String getErrorMessage() {
		return null;
	}

	public char[] getContextInformationAutoActivationCharacters() {
		return null;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return null;
	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return null;
	}

	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		String[] builtins = new String[] { "$form", "$flash" };
		try {
			List<ICompletionProposal> proposals = new ArrayList<ICompletionProposal>();
			int dollar = viewer.getDocument().get().lastIndexOf('$', offset - 1);
			String prefix = null;
			if (dollar != -1)
				prefix = viewer.getDocument().get(dollar, offset - dollar);

			/*
			 * TODO: Consider the AST here. Are we going to be able to use the
			 * normal Viento AST? I'd certainly like to. Completions happen when
			 * the syntax is incomplete. In Viento, that either means a parse
			 * error, or a Statement followed by a Text node in which the
			 * beginnings of the next Call are being typed. If we take this into
			 * account, using the AST in conjunction with simple text searching
			 * like I'm doing here, we'll probably be able to survive without a
			 * completely new parser.
			 */

			for (String proposal : builtins) {
				String completion = proposal;
				if (prefix == null)
					proposals.add(new CompletionProposal(completion, offset, 0, completion.length(), null, proposal, null, null));
				else if (proposal.startsWith(prefix)) {
					completion = proposal.substring(prefix.length());
					proposals.add(new CompletionProposal(completion, offset, 0, completion.length(), null, proposal, null, null));
				}
			}
			return (ICompletionProposal[]) proposals.toArray(new ICompletionProposal[proposals.size()]);
		} catch (Exception e) {
			return null;
		}
	}
}