FROM node:10.15.3-jessie

ENV NODE_ENV=production

RUN mkdir /app
WORKDIR /app

COPY package.json package-lock.json ./

COPY . .

RUN npm install --production

RUN npm install pm2 -g

EXPOSE 4000

CMD [ "pm2-runtime", "npm", "--", "start" ]