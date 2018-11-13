const { client } = require("../utils/elasticsearch");

class ProductRepository {
  async getProduct(stockCode) {
    const response = await client.search({
      index: "products",
      body: {
        query: {
          match: {
            stockCode
          }
        }
      }
    });
    return response.hits.hits.map(x => x._source)[0];
  }
  async searchProduct(searchQuery) {
    const response = await client.search({
      index: "products",
      body: {
        query: {
          match: {
            description: searchQuery
          }
        }
      }
    });
    return response.hits.hits.map(x => x._source);
  }
}

module.exports = ProductRepository;