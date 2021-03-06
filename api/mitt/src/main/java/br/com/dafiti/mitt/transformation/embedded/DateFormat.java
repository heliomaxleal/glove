/*
 * Copyright (c) 2019 Dafiti Group
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */
package br.com.dafiti.mitt.transformation.embedded;

import br.com.dafiti.mitt.transformation.Parser;
import br.com.dafiti.mitt.transformation.Transformable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Valdiney V GOMES
 */
public class DateFormat implements Transformable {

    private Parser parser;
    private List<Object> record;
    private final String field;
    private final String inputFormat;
    private final String outputFormat;

    public DateFormat(String field, String outputFormat) {
        this.field = field;
        this.outputFormat = outputFormat;
        this.inputFormat = "yyyy-MM-dd HH:mm:ss";
    }

    public DateFormat(String field, String inputFormat, String outputFormat) {
        this.field = field;
        this.inputFormat = inputFormat;
        this.outputFormat = outputFormat;
    }

    @Override
    public void init(
            Parser parser,
            List<Object> record) {

        this.parser = parser;
        this.record = record;
    }

    @Override
    public String getValue() {
        String value = new String();
        Object date = parser.evaluate(record, field);

        if (date != null) {
            if (date instanceof Date) {
                value = new SimpleDateFormat(outputFormat).format(date);
            } else {
                try {
                    value = new SimpleDateFormat(outputFormat)
                            .format(
                                    new SimpleDateFormat(inputFormat)
                                            .parse((String) date));
                } catch (ParseException ex) {
                    Logger.getLogger(DateFormat.class.getName()).log(Level.SEVERE, "Error converting data " + date, ex);
                }
            }
        }

        return value;
    }
}
