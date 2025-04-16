import struct
import math

# ---------------------------
# MD5 Implementation (RFC 1321)
# ---------------------------
def left_rotate(x, amount):
    """Left rotate a 32-bit integer x by 'amount' bits."""
    x &= 0xFFFFFFFF
    return ((x << amount) | (x >> (32 - amount))) & 0xFFFFFFFF

def md5(message):
    """
    Computes MD5 hash of the given message (bytes or str).
    
    Args:
        message (bytes or str): The input message.
        
    Returns:
        str: The MD5 digest in hexadecimal format.
    """
    if isinstance(message, str):
        message = message.encode('utf-8')

    original_len_in_bits = (8 * len(message)) & 0xffffffffffffffff
    message += b'\x80'
    while (len(message) * 8) % 512 != 448:
        message += b'\x00'
    message += struct.pack('<Q', original_len_in_bits)

    a0, b0, c0, d0 = 0x67452301, 0xefcdab89, 0x98badcfe, 0x10325476

    s = ([7, 12, 17, 22] * 4 +
         [5, 9, 14, 20] * 4 +
         [4, 11, 16, 23] * 4 +
         [6, 10, 15, 21] * 4)

    T = [int((1 << 32) * abs(math.sin(i + 1))) & 0xFFFFFFFF for i in range(64)]

    for chunk_offset in range(0, len(message), 64):
        chunk = message[chunk_offset:chunk_offset+64]
        M = list(struct.unpack('<16I', chunk))

        A, B, C, D = a0, b0, c0, d0

        for i in range(64):
            if 0 <= i <= 15:
                F = (B & C) | ((~B) & D)
                g = i
            elif 16 <= i <= 31:
                F = (D & B) | ((~D) & C)
                g = (5 * i + 1) % 16
            elif 32 <= i <= 47:
                F = B ^ C ^ D
                g = (3 * i + 5) % 16
            else:
                F = C ^ (B | (~D))
                g = (7 * i) % 16

            F = (F + A + T[i] + M[g]) & 0xFFFFFFFF
            A, D, C, B = D, C, B, (B + left_rotate(F, s[i])) & 0xFFFFFFFF

        a0 = (a0 + A) & 0xFFFFFFFF
        b0 = (b0 + B) & 0xFFFFFFFF
        c0 = (c0 + C) & 0xFFFFFFFF
        d0 = (d0 + D) & 0xFFFFFFFF

    return ''.join(struct.pack('<I', i).hex() for i in [a0, b0, c0, d0])

# ---------------------------
# SHA-1 Implementation
# ---------------------------
def left_rotate_32(x, n):
    """Left rotate a 32-bit integer x by n bits."""
    return ((x << n) | (x >> (32 - n))) & 0xffffffff

def sha1(message):
    """
    Computes SHA-1 hash of the given message (bytes or str).
    
    Args:
        message (bytes or str): The input message.
        
    Returns:
        str: The SHA-1 digest in hexadecimal format.
    """
    if isinstance(message, str):
        message = message.encode('utf-8')

    original_byte_len = len(message)
    original_bit_len = original_byte_len * 8

    message += b'\x80'
    while (len(message) * 8) % 512 != 448:
        message += b'\x00'
    message += struct.pack('>Q', original_bit_len)

    h0 = 0x67452301
    h1 = 0xEFCDAB89
    h2 = 0x98BADCFE
    h3 = 0x10325476
    h4 = 0xC3D2E1F0

    for chunk_offset in range(0, len(message), 64):
        chunk = message[chunk_offset:chunk_offset+64]
        w = list(struct.unpack('>16I', chunk))
        for i in range(16, 80):
            w.append(left_rotate_32(w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16], 1))

        a, b, c, d, e = h0, h1, h2, h3, h4

        for i in range(80):
            if 0 <= i <= 19:
                f = (b & c) | ((~b) & d)
                k = 0x5A827999
            elif 20 <= i <= 39:
                f = b ^ c ^ d
                k = 0x6ED9EBA1
            elif 40 <= i <= 59:
                f = (b & c) | (b & d) | (c & d)
                k = 0x8F1BBCDC
            else:
                f = b ^ c ^ d
                k = 0xCA62C1D6

            temp = (left_rotate_32(a, 5) + f + e + k + w[i]) & 0xffffffff
            a, b, c, d, e = temp, a, left_rotate_32(b, 30), c, d

        h0 = (h0 + a) & 0xffffffff
        h1 = (h1 + b) & 0xffffffff
        h2 = (h2 + c) & 0xffffffff
        h3 = (h3 + d) & 0xffffffff
        h4 = (h4 + e) & 0xffffffff

    return '{:08x}{:08x}{:08x}{:08x}{:08x}'.format(h0, h1, h2, h3, h4)

if __name__ == '__main__':
    test_message = "Rohith Dasari found a bug in the code"
    print("MD5  :", md5(test_message))
    print("SHA-1:", sha1(test_message))
