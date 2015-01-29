package net.canadensys.web;
import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ContentBlockExtractingRule;
import org.sitemesh.tagprocessor.State;

/**
 * Similar to Sm2TagRuleBundle but only extract content blocks.
 * @author cgendreau
 *
 */
public class ContentBlockTagRuleBundle implements TagRuleBundle{
	
    public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        // <content> blocks
        defaultState.addRule("content", new ContentBlockExtractingRule(contentProperty.getChild("page")));
    }

    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        // No op.
    }


}
