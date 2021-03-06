/*
 *  Copyright 2012 GWT-Bootstrap
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.gwtbootstrap.client.ui;

import java.util.List;

import com.github.gwtbootstrap.client.ui.base.DivWidget;
import com.github.gwtbootstrap.client.ui.resources.Bootstrap;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

//@formatter:off
/**
 * The container for a tabbable nav.
 * 
 * @since 2.0.4.0
 * @author Dominik Mayer
 * @author ohashi keisuke
 */
//@formatter:on
public class TabPanel extends DivWidget {

    private static class TabContent extends DivWidget {

        public TabContent() {
            setStyleName(Bootstrap.tab_content);
        }
    }

    private NavTabs tabs = new NavTabs();

    private TabContent tabContent = new TabContent();

    public TabPanel() {
        this(Bootstrap.Tabs.ABOVE);
    }

    public TabPanel(Bootstrap.Tabs position) {
        setTabPosition(position.get().toLowerCase());
    }

    public void setTabPosition(String position) {
        if(tabs.getParent() != null) {
            remove(tabs);
            remove(tabContent);
        }
        
        if (position.equalsIgnoreCase("below")) {
            setStyle(Bootstrap.Tabs.BELOW);
            super.add(tabContent);
            super.add(tabs);
        } else if (position.equalsIgnoreCase("left")) {
            setStyle(Bootstrap.Tabs.LEFT);
            super.add(tabs);
            super.add(tabContent);
        } else if (position.equalsIgnoreCase("right")) {
            setStyle(Bootstrap.Tabs.RIGHT);
            super.add(tabs);
            super.add(tabContent);
        } else {
            setStyle(Bootstrap.Tabs.ABOVE);
            super.add(tabs);
            super.add(tabContent);
        }
    }

    @Override
    public void add(Widget child) {

        if (child instanceof TabPane) {
            add((TabPane) child);
            return;
        }

        if (child instanceof TabLink) {
            add((TabLink) child);
            return;
        }
        
        if(child instanceof DropdownTab) {
            add((DropdownTab) child);
            return;
        }
        
        if(GWT.isProdMode()) {
            throw new IllegalArgumentException("TabPanel can add only TabPane or TabLink or Tab or DorpdownTab. you added " + child);
        }
    }
    
    private void add(DropdownTab dropdownTab) {
        
        tabs.add(dropdownTab);
        
        List<Tab> tabList = dropdownTab.getTabList();
        for (Tab tab : tabList) {
            TabPane tabPane = tab.getTabPane();
            tabContent.add(tabPane);
        }
    }

    private void add(TabPane child) {

        if(child.isCreateTabLink()) {
            TabLink tabLink = new TabLink(child);
            tabs.add(tabLink);
        }
        tabContent.add(child);
    }
    
    private void add(final TabLink child) {
        
        if(child.isCreateTabPane() && child.getTabPane() == null){
            TabPane pane = new TabPane(child.getText());
            child.setTablePane(pane);
            tabContent.add(pane);
        } else if(child.getTabPane() != null) {
            tabContent.add(child.getTabPane());
        }
        tabs.add(child);
    }
    
    @Override
    public void clear() {
        tabContent.clear();
        tabs.clear();
    }
    
    /**
     * Remove tab or tabpane.
     * <p>
     * If Tablink has TabPane,romve TabPane with TabLink.
     * </pre>
     * {@inheritDoc}
     */
    @Override
    public boolean remove(int index) {
        Widget widget = tabs.getWidget(index);
        
        if (widget instanceof TabLink) {
            TabLink link = (TabLink) widget;
            if(link.getTabPane() != null) {
                link.getTabPane().removeFromParent();
            }
            return tabs.remove(index);
        } else if(widget instanceof TabPane) {
            return tabContent.remove(widget);
        }
        
        return super.remove(widget);
    }
    
    /**
     * remove TabLink or TabPane.
     * <p>
     * </p>
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Widget w) {
        
        if (w instanceof TabLink) {
            TabLink link = (TabLink) w;
            
            if(link.getTabPane() != null) {
                link.getTabPane().removeFromParent();
            }
            return tabs.remove(w);
        } else if(w instanceof TabPane) {
            return tabContent.remove(w);
        }
        
        return super.remove(w);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(IsWidget child) {
        
        if (child instanceof Tab) {
            Tab tab = (Tab) child;
            
            TabLink link = tab.asTabLink();
            
            if(link.getTabPane() != null) {
                link.getTabPane().removeFromParent();
            }
            return tabs.remove(link);
        } else if(child instanceof DropdownTab) {
            DropdownTab tab = (DropdownTab)child;
            
            List<Tab> tabList = tab.getTabList();
            
            for (Tab tab2 : tabList) {
                tabContent.remove(tab2.getTabPane());
            }
            return super.remove(child);
        }
        
        return super.remove(child);
    }
}
