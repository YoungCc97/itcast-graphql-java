package cn.itcast.graphql.demo;

import cn.itcast.graphql.vo.Card;
import cn.itcast.graphql.vo.User;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.apache.commons.io.IOUtils;

import java.io.IOException;

/**
 * @author Young
 * @date 2019/12/17 15:30
 * @description：SDL测试用例
 */
public class GraphQLSDLDemo {
    public static void main(String[] args) throws IOException {

        String fileName = "user.graphql";
        String fileContent = IOUtils.toString(GraphQLSDLDemo.class.getClassLoader().getResource(fileName), "UTF-8");
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(fileContent);
        //解决的是数据的查询
        RuntimeWiring wiring = RuntimeWiring.newRuntimeWiring()
                .type("UserQuery", builder ->
                        builder.dataFetcher("user", dataFetchingEnvironment -> {
                            Long id = dataFetchingEnvironment.getArgument("id");
                            Card card = new Card("12345678",id);
                            return new User(id,"张三："+id,20 + id.intValue(),card);
                        })
                )
                .build();
        //生成schema
        GraphQLSchema graphQLSchema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);

        //根据schema对象生成graphql对象
        GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

        String query = "{user(id:2){id,name,age,card{cardNumber,userId}}}";
        ExecutionResult result = graphQL.execute(query);
        System.out.println(result.toSpecification());
    }
}
