package org.opensails.sails.url;

public interface IEventUrl {
    /**
     * @return the url that triggered this event, up to and including the
     *         context
     */
    String getAbsoluteContextUrl();

    /**
     * @return the url that triggered this event, including all parameters
     */
    String getAbsoluteUrl();

    /**
     * @return the url that triggered this event, up to and including the
     *         servlet
     */
    String getAbsolutServletUrl();

    /**
     * @return the name of the action this event is destined for. The may be
     *         modified and not be equal to {@link #getAbsoluteUrl()}
     */
    String getAction();

    String[] getActionParameters();

    /**
     * @return the action url, which includes the controller/action/param/param
     */
    String getActionUrl();

    /**
     * @return the name of the controller this event is destined for. The may be
     *         modified and not be equal to {@link #getAbsoluteUrl()}
     */
    String getController();
}