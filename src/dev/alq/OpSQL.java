/*
 * (c) Copyright 2006 Hewlett-Packard Development Company, LP
 * All rights reserved.
 * [See end of file]
 */

package dev.alq;

import java.util.List;

import com.hp.hpl.jena.query.core.Var;
import com.hp.hpl.jena.query.engine1.ExecutionContext;
import com.hp.hpl.jena.query.engine2.Algebra;
import com.hp.hpl.jena.query.engine2.Table;
import com.hp.hpl.jena.query.engine2.TableFactory;
import com.hp.hpl.jena.query.engine2.op.Evaluator;
import com.hp.hpl.jena.query.engine2.op.OpExtBase;
import com.hp.hpl.jena.query.util.IndentedWriter;
import com.hp.hpl.jena.sdb.core.sqlnode.GenerateSQL;
import com.hp.hpl.jena.sdb.core.sqlnode.SqlNode;
import com.hp.hpl.jena.sdb.store.Store;

public class OpSQL extends OpExtBase
{
    private SqlNode sqlNode ;
    private Store store ;
    List<Var> projectVars = null ;
    
    public OpSQL(Store store, SqlNode sqlNode)
    {
        this.store = store ;
        this.sqlNode = sqlNode ;
    }

    public void setProjectVars(List<Var> projectVars) { this.projectVars = projectVars ; }
    
    public Table eval(Evaluator evaluator)
    {
        ExecutionContext execCxt = evaluator.getExecContext() ;
        return TableFactory.create(new QueryIterSDB(execCxt.getQuery(),
                                                    store, 
                                                    this, 
                                                    Algebra.makeRoot(execCxt), execCxt)) ;
    }

    @Override
    public void output(IndentedWriter out)
    {
        out.println("(OpSQL --------") ;
        out.incIndent() ;
        sqlNode.output(out) ;
        out.decIndent() ;
        out.ensureStartOfLine() ;
        out.print("--------)") ;
    }
    
    public String toSQL()
    {
        return GenerateSQL.toSQL(sqlNode) ;
    }

    public SqlNode getSqlNode()
    {
        return sqlNode ;
    }
}

/*
 * (c) Copyright 2006 Hewlett-Packard Development Company, LP
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */