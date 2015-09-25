package zx.blog.article.utils.htmlTag;

import org.htmlparser.tags.CompositeTag;

/**
 * A span tag.
 */
public class Pre extends CompositeTag{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3944646880879405331L;
	/**
     * The set of names handled by this tag.
     */
    private static final String[] mIds = new String[] {"PRE"};

    /**
     * Create a new pre tag.
     */
    public Pre ()
    {
    }

    /**
     * Return the set of names handled by this tag.
     * @return The names to be matched that create tags of this type.
     */
    public String[] getIds ()
    {
        return (mIds);
    }
}
