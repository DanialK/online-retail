const ProductRepository = require("./repositories/ProductRepository");
const MeasureRepository = require("./repositories/MeasureRepository");

const productRepository = new ProductRepository();
const measureRepository = new MeasureRepository();

// The root provides a resolver function for each API endpoint
const resolvers = {
  Query: {
    product: (root, args, context, info) => {
      return productRepository.getProduct(args.stockCode);
    },
    productSearch: (root, args, context, info) => {
      return productRepository.searchProduct(args.searchQuery);
    },
    measures: (root, args, context, info) => {
      const { type, stockCode, startDate, endDate } = args;
      startDate = startDate ? new Date(startDate) : new Date();
      endDate = endDate ? new Date(endDate) : new Date();
      return measureRepository.getMeasures(stockCode, type, startDate, endDate);
    }
  },
  Product: {
    measures(product, args, context, info) {
      const { type, startDate, endDate } = args;
      return measureRepository.getMeasures(product.stockCode, type, startDate, endDate);
    }
  }
};

module.exports = resolvers;
