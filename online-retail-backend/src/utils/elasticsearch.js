var elasticsearch = require('elasticsearch');

var client = new elasticsearch.Client({
  host: process.env.ELASTICSEARCH || 'localhost:9200'
});

exports.client = client;