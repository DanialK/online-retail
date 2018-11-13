import React from 'react';
import ReactDOM from 'react-dom';
import ApolloClient from "apollo-boost";
import { ApolloProvider } from "react-apollo";
import './index.css';
import App from './App';

const client = new ApolloClient({
  uri: "http://localhost:4000/graphql"
});

ReactDOM.render((
  <ApolloProvider client={client}>
    <App client={client} />
  </ApolloProvider>
), document.getElementById('root'));
