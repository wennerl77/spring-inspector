package org.inspector.core.report;

import org.inspector.utils.color.AnsiColor;
import org.inspector.utils.color.PrintColorUtil;

public enum Severity {
    INFO,
    WARNING,
    ERROR;

    @Override
    public String toString() {
        String colorize = "";
        switch (this) {
            case INFO -> colorize = PrintColorUtil.colorize(this.name(), AnsiColor.BLUE);
            case WARNING -> colorize = PrintColorUtil.colorize(this.name(), AnsiColor.YELLOW);
            case ERROR -> colorize = PrintColorUtil.colorize(this.name(), AnsiColor.RED);
        }

        return colorize;
    }
}
