const fs = require("fs");
const path = require("path");
const express = require('express');
const { ApolloServer } = require('apollo-server-express');
const typeDefs = fs.readFileSync(path.join(__dirname, "schema.graphql"), "utf8");
const resolvers = require("./resolvers");

const app = express();

const server = new ApolloServer({
  typeDefs,
  resolvers,
});

server.applyMiddleware({ app });

app.listen({ port: 4000 }, () =>
  console.log(`🚀 Server ready at http://localhost:4000${server.graphqlPath}`)
);
