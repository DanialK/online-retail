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

const port = process.env.PORT || 4000;

app.get("/health", (req, res) => {
  res.send("Ok");
});

app.listen({ port }, () =>
  console.log(`🚀 Server ready at http://localhost:${port}${server.graphqlPath}`)
);
