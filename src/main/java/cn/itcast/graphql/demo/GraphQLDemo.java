package cn.itcast.graphql.demo;

import cn.itcast.graphql.vo.User;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.Scalars;
import graphql.schema.*;
import org.omg.CORBA.Environment;

import static graphql.Scalars.*;
import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.*;
import static graphql.schema.GraphQLObjectType.newObject;

/**
 * @author Young
 * @date 2019/12/17 14:10
 * @description：
 */
public class GraphQLDemo {
    public static void main(String[] args){

        /**
         * @author Young
         * @date 2019/12/17 14:36
         * @param args
         * @description：type User { #定义对象
        */
        GraphQLObjectType userObjectType = newObject()
                .name("User")
                .field(newFieldDefinition().name("id").type(GraphQLLong))
                .field(newFieldDefinition().name("name").type(GraphQLString))
                .field(newFieldDefinition().name("age").type(GraphQLInt))
                .build();

        /**
         * @author Young
         * @date 2019/12/17 14:37
         * @param args
         * @description：user : User #指定对象以及参数类型
        */
        GraphQLFieldDefinition userFieldDefinition = newFieldDefinition()
                .name("user")
                .type(userObjectType)
                .argument(newArgument().name("id").type(GraphQLLong).build())
                //静态查询
//                .dataFetcher(new StaticDataFetcher(new User(1L,"张三",20)))
                //
                .dataFetcher(environment->{
                    Long id = environment.getArgument("id");
                    //查询数据库
                    //TODO 先模拟实现
                    return new User(id,"张三："+id,20 + id.intValue());
                })
                .build();

        /**
         * @author Young
         * @date 2019/12/17 14:38 
         * @param args
         * @description：type UserQuery { #定义查询的类型 }
        */   
        GraphQLObjectType userQueryObjectType = newObject()
                .name("UserQuery")
                .field(userFieldDefinition)
                .build();

        /**
         * @author Young
         * @date 2019/12/17 14:39
         * @param args
         * @description：schema { #定义查询 }
        */
        GraphQLSchema graphQLSchema = GraphQLSchema.newSchema().query(userQueryObjectType).build();

        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        String query = "{user(id:2){id,name}}";
        ExecutionResult result = graphQL.execute(query);

//        System.out.println(result.getErrors());
//        System.out.println(result.getData());
        System.out.println(result.toSpecification());
    }
}
