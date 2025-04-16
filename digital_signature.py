import hashlib
import random

# Prime modulus p, subprime q, and base g
p = 8009  # large prime
q = 101   # prime divisor of p-1
g = 2     # generator such that g^q mod p == 1

# ---------------------------
# Key Generation
# ---------------------------
def generate_keys():
    x = random.randint(1, q - 1)       # Private key: x
    y = pow(g, x, p)                   # Public key: y = g^x mod p
    return x, y

# ---------------------------
# SHA-1 Hash Function (Built-in)
# ---------------------------
def sha1_hash(message):
    if isinstance(message, str):
        message = message.encode('utf-8')
    return int(hashlib.sha1(message).hexdigest(), 16)

# ---------------------------
# Signature Generation
# ---------------------------
def sign(message, x):
    h = sha1_hash(message) % q
    while True:
        k = random.randint(1, q - 1)
        r = pow(g, k, p) % q
        if r == 0:
            continue
        k_inv = pow(k, -1, q)  # Modular inverse of k mod q
        s = (k_inv * (h + x * r)) % q
        if s != 0:
            break
    return r, s

# ---------------------------
# Signature Verification
# ---------------------------
def verify(message, signature, y):
    r, s = signature
    if not (0 < r < q and 0 < s < q):
        return False
    h = sha1_hash(message) % q
    w = pow(s, -1, q)
    u1 = (h * w) % q
    u2 = (r * w) % q
    v = ((pow(g, u1, p) * pow(y, u2, p)) % p) % q
    return v == r


if __name__ == '__main__':
    message = "Hello, Digital Signature!"

    # Key generation
    private_key, public_key = generate_keys()

    # Signing
    signature = sign(message, private_key)
    print("Signature (r, s):", signature)

    # Verifying
    valid = verify(message, signature, public_key)
    print("Signature valid?", valid)
