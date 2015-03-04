package com.siercuit.cartelera.mappers;

import java.util.List;

public class DrawerNavItemMapper
{
	
	private String title;
    private String subtitle;
    private String fragment;
	private int icon;
    private int color;
    private boolean hasSubtitle;
	private boolean hasChild;
    private List<String> childTitles;
    private List<Integer> childIcons;
    private List<String> childFragments;

	public DrawerNavItemMapper(String title, int icon, int color, String fragment)
    {
		this.title = title;
        this.hasSubtitle = false;
		this.icon = icon;
        this.color = color;
        this.fragment = fragment;
        this.hasChild = false;
	}

    public DrawerNavItemMapper(String title, String subtitle, int icon, int color, String fragment)
    {
        this.title = title;
        this.subtitle = subtitle;
        this.hasSubtitle = true;
        this.icon = icon;
        this.color = color;
        this.fragment = fragment;
        this.hasChild = false;
    }

    public DrawerNavItemMapper(String title, int icon, int color, List<String> childTitles,
                               List<Integer> childIcons, List<String> childFragments)
    {
        this.title = title;
        this.hasSubtitle = false;
        this.icon = icon;
        this.color = color;
        this.hasChild = true;
        this.childTitles = childTitles;
        this.childIcons = childIcons;
        this.childFragments = childFragments;
    }
	
	public String getTitle(){
		return this.title;
	}

    public String getSubtitle()
    {
        if (hasSubtitle) {
            return this.subtitle;
        } else {
            return null;
        }
    }

	public int getIcon(){
		return this.icon;
	}

    public int getColor() { return this.color; }

    public String getFragment() { return this.fragment; }

    public String getChildTitle(Integer id)
    {
        if (hasChild) {
            return childTitles.get(id);
        } else {
            return null;
        }
    }

    public int getChildIcon(Integer id)
    {
        if (hasChild) {
            return childIcons.get(id);
        } else {
            return 0;
        }
    }

    public String getChildFragment(Integer id)
    {
        if (hasChild) {
            return childFragments.get(id);
        } else {
            return null;
        }
    }

    public Integer getChildItemsSize()
    {
        if (hasChild) {
            return childTitles.size();
        } else {
            return 0;
        }
    }

    public Boolean hasChild() { return hasChild; }

}
