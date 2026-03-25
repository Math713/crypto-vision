# CryptoVision

Web platform for cryptocurrency investment — SPA + REST API + PostgreSQL.

Allows buying, selling and real-time tracking of crypto assets, with secure authentication (JWT + 2FA), role-based access control (RBAC) and interactive dashboards.

> Academic project — FIAP, 2nd Year Software Engineering (2026)

---

## Repository Structure
```
crypto-vision/
├── frontend/        # React SPA — dashboards and charts
├── backend/         # Java + Spring Boot — REST API
└── db/
    └── migration/
        └── v1__init_schema.sql   # Initial PostgreSQL schema
```

## Stack

| Layer | Technology |
|---|---|
| Frontend | React.js |
| Backend | Java + Spring Boot + Spring Security |
| Database | PostgreSQL |
| Authentication | JWT + Refresh Token + optional 2FA |
| Access Control | RBAC (user, manager, administrator) |
| Infrastructure | Docker + Docker Compose |

## Database

Schema located at `db/migration/v1__init_schema.sql`:

- **usuarios** — user registration and profile (`iniciante`, `experiente`, `corporativo`)
- **autenticacao** — refresh token, 2FA and session control
- **criptomoedas** — asset registry with current price
- **historico_precos** — historical price series
- **portfolios** — each user's position per asset
- **transacoes** — record of buys, sells, deposits and withdrawals
- **roles** — user roles (`usuario`, `gestor`, `administrador`)
- **alertas** — price-target notifications

## Status

🚧 In progress — database schema defined, folder structure set up.

| Module | Status |
|---|---|
| Database schema | ✅ Done |
| Backend (REST API) | 🚧 In progress |
| Frontend (SPA) | 🚧 In progress |
| Docker setup | 📋 Planned |
