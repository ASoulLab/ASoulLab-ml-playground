/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.common.swingui.timetable;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.Scrollable;

import org.optaplanner.examples.common.swingui.SolutionPanel;

public class TimeTablePanel<XObject, YObject> extends JPanel implements Scrollable  {

    private TimeTableLayout layout = new TimeTableLayout();
    private Map<Object, Integer> xMap = new HashMap<Object, Integer>();
    private Map<Object, Integer> yMap = new HashMap<Object, Integer>();

    public TimeTablePanel() {
        setLayout(layout);
    }

    public void reset() {
        removeAll();
        layout.reset();
        xMap.clear();
        yMap.clear();
    }

    // ************************************************************************
    // Define methods
    // ************************************************************************

    public void defineColumnHeaderByKey(HeaderColumnKey xObject) {
        int x = layout.addColumn();
        xMap.put(xObject, x);
    }

    public void defineColumnHeader(XObject xObject) {
        int x = layout.addColumn();
        xMap.put(xObject, x);
    }

    public void defineColumnHeader(XObject xObject, int width) {
        int x = layout.addColumn(width);
        xMap.put(x